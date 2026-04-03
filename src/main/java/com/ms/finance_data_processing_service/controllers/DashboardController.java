package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.dtos.request.BalanceQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.dtos.response.BalanceResponseDto;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.DashboardService;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlUtil.DASHBOARD_URL)
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/balance")
    public ResponseEntity<ApiResponseDto<BalanceResponseDto>> getBalance(
            @ModelAttribute @Valid BalanceQueryRequestDto query
            ) {
        System.out.println(query.getEndDate());
        BalanceResponseDto apiResponse = dashboardService.getBalance(query);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(), "Balance fetch successfully", apiResponse));
    }
}
