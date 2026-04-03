package com.ms.finance_data_processing_service.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BaseDateRangeDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @AssertTrue(message = "endDate must be after or equal to startDate")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !endDate.isBefore(startDate);
    }
}
