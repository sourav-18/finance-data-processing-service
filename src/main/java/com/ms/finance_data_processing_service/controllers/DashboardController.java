package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.dtos.request.BalanceQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.request.CategoryAmountQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.request.DateUnitRequestDto;
import com.ms.finance_data_processing_service.dtos.response.*;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.DashboardService;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlUtil.DASHBOARD_URL)
@RequiredArgsConstructor
@Validated
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/balance")
    public ResponseEntity<ApiResponseDto<BalanceResponseDto>> getBalance(
            @ModelAttribute @Valid BalanceQueryRequestDto query
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Balance fetch successfully",
                        dashboardService.getBalance(query)));
    }

    @GetMapping("/category-amounts")
    public ResponseEntity<ApiResponseDto<List<CategoryAmountResponseDto>>> getCategoryAmount(
            @ModelAttribute @Valid CategoryAmountQueryRequestDto query
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Category amount fetch successfully",
                        dashboardService.getCategoryAmount(query)));

    }

    @GetMapping("/balance-by-month")
    public ResponseEntity<ApiResponseDto<List<BalanceByMonthResponseDto>>> getBalanceByMonth(
            @Valid @ModelAttribute DateUnitRequestDto dateUnit
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Monthly balance fetch successfully",
                        dashboardService.getBalanceByMonth(dateUnit)));

    }

    @GetMapping("/balance-by-week")
    public ResponseEntity<ApiResponseDto<List<BalanceByWeekResponseDto>>> getBalanceByWeek(
            @Valid @ModelAttribute DateUnitRequestDto dateUnit
            ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Weekly balance fetch successfully",
                        dashboardService.getBalanceByWeek(dateUnit)));

    }

}
