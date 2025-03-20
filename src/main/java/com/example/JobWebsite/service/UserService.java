package com.example.JobWebsite.service;

import com.example.JobWebsite.cloudinary.CloudinaryHelper;
import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.dto.api.ApiResponse;
import com.example.JobWebsite.dto.email.EmailDetails;
import com.example.JobWebsite.dto.user.*;
import com.example.JobWebsite.entity.JobEntity;
import com.example.JobWebsite.entity.UserEntity;
import com.example.JobWebsite.exceptionhandler.AppException;
import com.example.JobWebsite.exceptionhandler.ErrorCode;
import com.example.JobWebsite.mapper.UserMapper;
import com.example.JobWebsite.redis.PresenceService;
import com.example.JobWebsite.repository.CustomRepository;
import com.example.JobWebsite.repository.JobRepository;
import com.example.JobWebsite.repository.UserRepository;
import com.example.JobWebsite.token.TokenHelper;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CustomRepository customRepository;
    private final EmailService emailService;
    private final PresenceService presenceService;
    private static Map<String, AtomicInteger> indexMap = new ConcurrentHashMap<>();
    private final JobRepository jobRepository;

    @Transactional
    public TokenResponse signUp(UserRequest signUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (Boolean.TRUE.equals(userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber()))) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        signUpRequest.setPassword(BCrypt.hashpw(signUpRequest.getPassword(), BCrypt.gensalt()));
        UserEntity userEntity = userMapper.getEntityFromRequest(signUpRequest);
        userEntity.setImageUrl(Common.IMAGE_DEFAULT);
        userRepository.save(userEntity);
        return TokenResponse.builder()
                .accessToken(TokenHelper.generateToken(userEntity))
                .role(userEntity.getRole())
                .build();
    }

    @Transactional
    public TokenResponse logIn(LogInRequest loginRequest) {
        UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername());
        if (Objects.isNull(userEntity)) {
            throw new AppException(ErrorCode.USERNAME_NOT_EXISTED);
        }

        if (!BCrypt.checkpw(loginRequest.getPassword(), userEntity.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        }
        return TokenResponse.builder()
                .accessToken(TokenHelper.generateToken(userEntity))
                .role(userEntity.getRole())
                .build();
    }

    @Transactional
    public ApiResponse<?> changeUserInformation(String accessToken,
                               ChangeInfoUserRequest changeInfoUserRequest,
                               MultipartFile imageUrl, MultipartFile backgroundImg) {
        Long userId = TokenHelper.getUserIdFromToken(accessToken);
        UserEntity userEntity = customRepository.getUserBy(userId);
        userMapper.updateEntityFromInput(userEntity, changeInfoUserRequest);

        if (Objects.nonNull(changeInfoUserRequest.getBirthdayString())) {
            String fixedDateStr = changeInfoUserRequest.getBirthdayString() + "T00:00:00+00:00";
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            OffsetDateTime birthday = OffsetDateTime.parse(fixedDateStr, formatter);
            userEntity.setBirthday(birthday);
        }
        if (Objects.nonNull(imageUrl)) {
            userEntity.setImageUrl(CloudinaryHelper.uploadAndGetFileUrl(imageUrl));
        }

        if (Objects.nonNull(backgroundImg)) {
            userEntity.setBackgroundImage(CloudinaryHelper.uploadAndGetFileUrl(backgroundImg));
        }

        userEntity.setDescription(changeInfoUserRequest.getDescription()
                .replaceAll("<li>|</li>|<ul>|</ul>|<br />", "")
        );

        userRepository.save(userEntity);
        return ApiResponse.builder()
                .code(200)
                .message("OK")
                .build();
    }

    @Transactional(readOnly = true)
    public ApiResponse<UserOutputV2> getUserInformation(String accessToken){
        Long userId = TokenHelper.getUserIdFromToken(accessToken);
        UserEntity userEntity = customRepository.getUserBy(userId);
        return ApiResponse.<UserOutputV2>builder()
                .message("OK")
                .code(200)
                .data(userMapper.getOutputFromEntity(userEntity))
                .build();
    }

    @Transactional
    public ApiResponse<?> sendCodeToEmail(String userName) {
        UserEntity userEntity = userRepository.findByUsername(userName);
        if (Objects.isNull(userEntity)) {
            throw new AppException(ErrorCode.USERNAME_NOT_EXISTED);
        }
        String code = generateCode();
        EmailDetails emailDetails = EmailDetails.builder()
                .subject("Reset Password")
                .recipient(userEntity.getEmail())
                .messageBody(code)
                .build();
        emailService.sendEmail(emailDetails);
        presenceService.plusCode(String.valueOf(userEntity.getId()), code);
        return ApiResponse.builder().build();
    }

    @Transactional
    public ApiResponse<?> recoverPassword(RecoverPassword recoverPassword) {
        UserEntity userEntity = userRepository.findByUsername(recoverPassword.getUsername());
        if (Objects.isNull(userEntity)) {
            throw new AppException(ErrorCode.USERNAME_NOT_EXISTED);
        }

        if (!recoverPassword.getCode().equals(presenceService.getCode(String.valueOf(userEntity.getId())))) {
            throw new AppException(ErrorCode.CODE_NOT_MATCH);
        } else {
            userEntity.setPassword(BCrypt.hashpw(recoverPassword.getNewPassword(), BCrypt.gensalt()));
            presenceService.delete(String.valueOf(userEntity.getId()));
            userRepository.save(userEntity);
        }
        return ApiResponse.builder().build();
    }

    @Transactional
    public void resetPassword(String accessToken, ResetPassword resetPassword) {
        Long userId = TokenHelper.getUserIdFromToken(accessToken);
        UserEntity userEntity = customRepository.getUserBy(userId);
        if (Objects.isNull(userEntity) || !BCrypt.checkpw(resetPassword.getOldPassword(), userEntity.getPassword())) {
            throw new RuntimeException(Common.INCORRECT_PASSWORD);
        }

        userEntity.setPassword(BCrypt.hashpw(resetPassword.getNewPassword(), BCrypt.gensalt()));
        userRepository.save(userEntity);
    }

    @Transactional
    public void setRole(String accessToken, String role) {
        Long userId = TokenHelper.getUserIdFromToken(accessToken);
        UserEntity userEntity = customRepository.getUserBy(userId);
        userEntity.setRole(role);
        userRepository.save(userEntity);
    }

    private String generateCode() {
        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        return uuid.substring(0, 6);
    }
}
