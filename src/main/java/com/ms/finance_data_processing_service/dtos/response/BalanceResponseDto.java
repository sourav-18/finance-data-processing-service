package com.ms.finance_data_processing_service.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BalanceResponseDto {
    private Long income;
    private Long expense;
    private Long netBalance;
    private Boolean isProfit;
}
