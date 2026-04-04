package com.ms.finance_data_processing_service.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BalanceByWeekResponseDto {
    private Long income=0L;
    private Long expense=0L;
    private Long netBalance=0L;
    @JsonIgnore
    private LocalDateTime dateTime;
    private String week;
    private LocalDateTime from;
    private LocalDateTime to;

    public BalanceByWeekResponseDto(Long income, Long expense) {
        this.income = income;
        this.expense = expense;
    }
    public BalanceByWeekResponseDto(String week,LocalDateTime from,LocalDateTime to){
        this.week=week;
        this.from=from;
        this.to=to;
    }
}
