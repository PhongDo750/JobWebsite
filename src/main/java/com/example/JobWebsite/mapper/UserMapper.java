package com.example.JobWebsite.mapper;


import com.example.JobWebsite.dto.user.ChangeInfoUserRequest;
import com.example.JobWebsite.dto.user.UserOutputV2;
import com.example.JobWebsite.dto.user.UserRequest;
import com.example.JobWebsite.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {
    UserOutputV2 getOutputFromEntity(UserEntity userEntity);
    UserEntity getEntityFromRequest(UserRequest signUpRequest);
    void updateEntityFromInput(@MappingTarget UserEntity userEntity, ChangeInfoUserRequest changeInfoUserRequest);
}
