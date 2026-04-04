package com.ms.finance_data_processing_service.dtos.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateUnitRequestDto {
    @Pattern(regexp = "^[1-9]\\d{3}$", message = "Year must be 4 digits and cannot start with 0")
    private String year;

    @Pattern(regexp = "^(1[0-2]|[1-9])$", message = "Month must be between 1 and 12")
    private String month;

    public Integer getIntegerMonth(){
        return month==null?null:Integer.valueOf(month);
    }
    public Integer getIntegerYear(){
        return year==null?null:Integer.valueOf(year);
    }

}
