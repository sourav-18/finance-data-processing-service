package com.ms.finance_data_processing_service.dtos.request;

import com.ms.finance_data_processing_service.dtos.validations.ValidEnum;
import com.ms.finance_data_processing_service.entites.Types.FinanceCategoryType;
import com.ms.finance_data_processing_service.entites.Types.FinanceStatusType;
import com.ms.finance_data_processing_service.entites.Types.FinanceType;
import com.ms.finance_data_processing_service.utils.ConstantUtil;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinanceUpdateRequestDto {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be at least {value}")
    @Max(value = ConstantUtil.MAX_FINANCE_AMOUNT, message = "Amount must not exceed {value}")
    private Long amount;

    @NotNull(message = "Type is required")
    @ValidEnum(enumClass = FinanceType.class,field = "Type")
    private String type;

    @NotNull(message = "category is required")
    @ValidEnum(enumClass = FinanceCategoryType.class,field = "Category")
    private String category;

    @NotNull(message = "status is required")
    @ValidEnum(enumClass = FinanceStatusType.class,field = "Status")
    private String status;

    private String note;
}
