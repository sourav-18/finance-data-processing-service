package com.ms.finance_data_processing_service.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ms.finance_data_processing_service.entites.Types.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinanceListRequestQueryDto extends BaseDateRangeDto{

    @Min(value = 1,message = "page should be minimum {value}")
    private int page=1;

    @Min(value = 1,message = "limit should be minimum {value}")
    @Max(value = 100,message = "limit should be Maximum {value}")
    private int limit=10;

    @Size(min = 1, max = 20, message = "search must be between {min} to {max} character")
    private String search;

    @Size(min = 2, max = 20, message = "sort must be between {min} to {max} character")
    private String sort;

    private FinanceStatusType status;

    private FinanceCategoryType category;

    private FinanceType type;

    @Min(value = 1,message = "createBy should be at least {value}")
    private Long createdBy;

    private String note;

    private Boolean deleteData=false;
}
