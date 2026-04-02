package com.ms.finance_data_processing_service.mappers;

import com.ms.finance_data_processing_service.dtos.request.UserCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.request.UserUpdateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.UserResponseDto;
import com.ms.finance_data_processing_service.entites.UserEntity;

public class UserMapper {
    public static UserResponseDto toResponseDto(UserEntity user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .status(user.getStatus())
                .email(user.getEmail())
                .role(user.getRole())
                .profilePicture(user.getProfilePicture())
                .cratedBy(user.getCreatedBy() != null ? user.getCreatedBy().getName() : null)
                .updateBy(user.getUpdatedBy() != null ? user.getUpdatedBy().getName() : null)
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static UserEntity toEntity(UserCreateRequestDto createBody){
        UserEntity newUser=new UserEntity();
        newUser.setName(createBody.getName());
        newUser.setEmail(createBody.getEmail());
        newUser.setRole(createBody.getRole());
        newUser.setProfilePicture(createBody.getProfilePicture());
        return newUser;
    }

    public static void updateEntity(UserEntity userEntity, UserUpdateRequestDto updateBody){
        userEntity.setName(updateBody.getName());
        userEntity.setEmail(updateBody.getEmail());
        userEntity.setRole(updateBody.getRole());
        userEntity.setProfilePicture(updateBody.getProfilePicture());
        userEntity.setStatus(updateBody.getStatus());
    }
}
