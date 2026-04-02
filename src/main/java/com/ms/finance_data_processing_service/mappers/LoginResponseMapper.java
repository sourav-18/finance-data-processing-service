package com.ms.finance_data_processing_service.mappers;

import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.dtos.response.LoginResponseDto;
import com.ms.finance_data_processing_service.entites.UserEntity;

public class LoginResponseMapper {
    public static LoginResponseDto toDto(UserEntity user, String token) {
        return LoginResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .profilePicture(user.getProfilePicture())
                .token(token).build();
    }
}
