package com.ms.finance_data_processing_service.dtos.request;

import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListRequestQueryDto extends BasePageLimitDto {

    @Size(min = 1 , max = 20 ,message = "search must be between 1 to 20 character")
    private String search;

    private UserRoleType role;

    private UserStatusType status;
}
