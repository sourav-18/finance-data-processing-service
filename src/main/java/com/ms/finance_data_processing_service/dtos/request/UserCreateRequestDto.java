package com.ms.finance_data_processing_service.dtos.request;

import com.ms.finance_data_processing_service.entites.Types.UserRoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDto {

    @NotBlank(message = "Name is required filed")
    @Size(min = 3 , max = 20 ,message = "Name must be between 3 to 20 character")
    private String name;

    @NotBlank(message = "Email is required filed")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required filed")
    @Size(min = 3 , max = 20 ,message = "password must be between 3 to 20 character")
    private String password;

    @NotNull(message = "Role is required filed")
    private UserRoleType role;

    private String profilePicture;
}
