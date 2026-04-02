package com.ms.finance_data_processing_service.services;


import com.ms.finance_data_processing_service.dtos.request.LoginRequestDto;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.dtos.response.LoginResponseDto;
import com.ms.finance_data_processing_service.entites.Types.UserStatusType;
import com.ms.finance_data_processing_service.entites.UserEntity;
import com.ms.finance_data_processing_service.exceptions.ApiError;
import com.ms.finance_data_processing_service.exceptions.ApiErrorException;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.mappers.LoginResponseMapper;
import com.ms.finance_data_processing_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDto login(LoginRequestDto loginBody) {
        UserEntity user = userRepository.findByEmail(loginBody.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(loginBody.getPassword(), user.getPassword())) {
            throw new ApiErrorException(ApiError.INVALID_EMAIL_PASSWORD);
        }

        if (user.getStatus() != UserStatusType.Active) {
            throw new ApiErrorException(ApiError.INACTIVE_USER);
        }

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().toString());

        String token = jwtService.generateToken(user.getId().toString(), claims);

        return LoginResponseMapper.toDto(user, token);
    }

}
