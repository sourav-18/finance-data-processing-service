package com.ms.finance_data_processing_service.controllers;


import com.ms.finance_data_processing_service.dtos.request.FinanceCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.dtos.response.FinanceResponseDto;
import com.ms.finance_data_processing_service.dtos.response.UserResponseDto;
import com.ms.finance_data_processing_service.entites.FinanceEntity;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.FinanceService;
import com.ms.finance_data_processing_service.utils.ConstantUtil;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping(UrlUtil.FINANCE_URL)
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponseDto<FinanceResponseDto>> create(
            HttpServletRequest request,
            @Valid @RequestBody FinanceCreateRequestDto financeCreateBody) {

        Long loginUserId = (Long) request.getAttribute(ConstantUtil.REQUEST_USER_ID);
        FinanceResponseDto apiResponse = financeService.create(financeCreateBody,loginUserId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseMapper.success(HttpStatus.CREATED.value(), "Finance record add successfully", apiResponse));

    }
}
