package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.dtos.request.LoginRequestDto;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.dtos.response.LoginResponseDto;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.AuthService;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UrlUtil.AUTH_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginBody) {
        LoginResponseDto apiResponse = authService.login(loginBody);
        return ResponseEntity.ok()
                .body(ApiResponseMapper.success(HttpStatus.OK.value(), "User login successfully", apiResponse));
    }
}
