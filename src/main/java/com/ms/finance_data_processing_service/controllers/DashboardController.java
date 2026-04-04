package com.ms.finance_data_processing_service.controllers;

import com.ms.finance_data_processing_service.dtos.request.*;
import com.ms.finance_data_processing_service.dtos.response.*;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import com.ms.finance_data_processing_service.services.DashboardService;
import com.ms.finance_data_processing_service.utils.UrlUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlUtil.DASHBOARD_URL)
@RequiredArgsConstructor
@Validated
public class DashboardController {

    private final DashboardService dashboardService;

    @PreAuthorize("hasAnyRole('Admin','Analyst')")
    @GetMapping("/balance")
    public ResponseEntity<ApiResponseDto<BalanceResponseDto>> getBalance(
            @ModelAttribute @Valid BalanceQueryRequestDto query
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Balance fetch successfully",
                        dashboardService.getBalance(query)));
    }

    @PreAuthorize("hasAnyRole('Admin','Analyst')")
    @GetMapping("/category-amounts")
    public ResponseEntity<ApiResponseDto<List<CategoryAmountResponseDto>>> getCategoryAmount(
            @ModelAttribute @Valid CategoryAmountQueryRequestDto query
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Category amount fetch successfully",
                        dashboardService.getCategoryAmount(query)));

    }

    @PreAuthorize("hasAnyRole('Admin','Analyst')")
    @GetMapping("/balance-by-month")
    public ResponseEntity<ApiResponseDto<List<BalanceByMonthResponseDto>>> getBalanceByMonth(
            @Valid @ModelAttribute BalanceByMonthQueryRequestDto query
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Monthly balance fetch successfully",
                        dashboardService.getBalanceByMonth(query)));

    }

    @PreAuthorize("hasAnyRole('Admin','Analyst')")
    @GetMapping("/balance-by-week")
    public ResponseEntity<ApiResponseDto<List<BalanceByWeekResponseDto>>> getBalanceByWeek(
            @Valid @ModelAttribute BalanceByWeekQueryRequestDto query
            ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponseMapper.success(HttpStatus.OK.value(),
                        "Weekly balance fetch successfully",
                        dashboardService.getBalanceByWeek(query)));

    }

}
