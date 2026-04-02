package com.ms.finance_data_processing_service.dtos.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePageLimitDto {
    @Min(value = 1,message = "page should be minimum 1")
    private int page=1;

    @Min(value = 1,message = "limit should be minimum 1")
    private int limit=10;
}