package com.ms.finance_data_processing_service.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BalanceByMonthResponseDto {
    private Long income=0L;
    private Long expense=0L;
    private Long netBalance=0L;
    @JsonIgnore
    private LocalDateTime monthWithDateTime;
    private String month;

    public BalanceByMonthResponseDto(Long income, Long expense, LocalDateTime monthWithDateTime) {
        this.income = income;
        this.expense = expense;
        this.monthWithDateTime = monthWithDateTime;
    }
    public BalanceByMonthResponseDto(String month){
        this.month=month;
    }
}
