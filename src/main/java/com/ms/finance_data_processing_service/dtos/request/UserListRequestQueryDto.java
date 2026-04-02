package com.ms.finance_data_processing_service.dtos.request;

import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListRequestQueryDto extends BasePageLimitDto {

    @Size(min = 1, max = 20, message = "search must be between 1 to 20 character")
    private String search;

    private UserRoleType role;

    @Min(value = 1, message = "sort can 1(asc) or 2(desc)")
    @Max(value = 2, message = "sort can 1(asc) or 2(desc)")
    private Integer sort;

    private UserStatusType status;
}
