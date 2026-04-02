package com.ms.finance_data_processing_service.dtos.response;

import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private UserRoleType role;
    private String profilePicture;
    private UserStatusType status;
    private LocalDateTime createdAt;
    private String cratedBy;
    private String updateBy;

    public UserResponseDto(
                           Long id,
                           String name,
                           String email,
                           UserStatusType status,
                           UserRoleType role,
                           String profilePicture,
                           String cratedBy,
                           String updateBy,
                           LocalDateTime createdAt
    ) {
        this.id = id;
        this.updateBy = updateBy;
        this.cratedBy = cratedBy;
        this.createdAt = createdAt;
        this.status = status;
        this.profilePicture = profilePicture;
        this.role = role;
        this.name = name;
        this.email = email;
    }
}
