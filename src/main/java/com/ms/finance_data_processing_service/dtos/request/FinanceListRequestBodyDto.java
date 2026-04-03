package com.ms.finance_data_processing_service.dtos.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FinanceListRequestBodyDto {
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "StartDate must be in yyyy-MM-dd format")
    private LocalDate startDate;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "EndDate must be in yyyy-MM-dd format")
    private LocalDate endDate;

    @AssertTrue(message = "endDate must be after or equal to startDate")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !endDate.isBefore(startDate);
    }
}
