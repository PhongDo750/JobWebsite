package com.example.JobWebsite.controller;

import com.example.JobWebsite.common.Common;
import com.example.JobWebsite.dto.api.ApiResponse;
import com.example.JobWebsite.dto.user.*;
import com.example.JobWebsite.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Lấy thông tin cá nhân")
    @GetMapping
    public ApiResponse<UserOutputV2> getUserInformation(@RequestHeader(value = Common.AUTHORIZATION) String accessToken){
        return userService.getUserInformation(accessToken);
    }

    // 2024-03-20T17:04:52.755Z
    @Operation(summary = "Thay đổi thông tin cá nhân")
    @PostMapping(value = "/change-user-information", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ApiResponse<?> changeUserInformation(@RequestPart("new_user_info") @Valid String changeInfoUserRequestString,
                                                @RequestHeader(value = Common.AUTHORIZATION) String accessToken,
                                                @RequestPart(value = "image", required = false) MultipartFile avatar,
                                                @RequestPart(value = "background_img", required = false) MultipartFile backgroundImg ) throws JsonProcessingException {
        ChangeInfoUserRequest changeInfoUserRequest;
        ObjectMapper objectMapper = new ObjectMapper();
        changeInfoUserRequest = objectMapper.readValue(changeInfoUserRequestString, ChangeInfoUserRequest.class);
        return userService.changeUserInformation(accessToken,changeInfoUserRequest, avatar, backgroundImg);
    }

    @Operation(summary = "Đăng ký tài khoản")
    @PostMapping("sign-up")
    public ApiResponse<TokenResponse> signUp(@RequestBody @Valid UserRequest signUpRequest){
        return ApiResponse.<TokenResponse>builder()
                .data(userService.signUp(signUpRequest))
                .code(200)
                .message("Đăng ký thành công")
                .build();
    }

    @PostMapping("log-in")
    public ApiResponse<TokenResponse> logIn(@RequestBody @Valid LogInRequest logInRequest) {
        return ApiResponse.<TokenResponse>builder()
                .data(userService.logIn(logInRequest))
                .code(200)
                .message("Đăng nhập thành công")
                .build();
    }

    @Operation(summary = "Lấy code để reset password")
    @PostMapping("/send-code-email")
    public ApiResponse<?> sendCodeToEmail(@RequestParam String username) {
        return userService.sendCodeToEmail(username);
    }

    @Operation(summary = "Lấy lại mật khẩu")
    @PostMapping("/recover-password")
    public ApiResponse<?> recoverPassword(@RequestBody @Valid RecoverPassword recoverPassword) {
        return userService.recoverPassword(recoverPassword);
    }

    @Operation(summary = "Thay đổi mật khẩu")
    @PostMapping("/reset-password")
    public void resetPassword(@RequestHeader(value = Common.AUTHORIZATION) String accessToken,
                              @RequestBody ResetPassword resetPassword) {
        userService.resetPassword(accessToken, resetPassword);
    }

    @Operation(summary = "Chọn role")
    @PostMapping("/set-role")
    public void setRole(@RequestHeader(Common.AUTHORIZATION) String accessToken,
                        @RequestParam String role) {
        userService.setRole(accessToken, role);
    }
}
