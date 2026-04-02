package com.ms.finance_data_processing_service.dtos.response;

import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private Long id;
    private String email;
    private String name;
    private UserRoleType role;
    private String profilePicture;
    private String token;
}
