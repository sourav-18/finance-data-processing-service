package com.ms.finance_data_processing_service.dtos.request;

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
    private FinanceType type;

    @NotNull(message = "category is required")
    private FinanceCategoryType category;

    @NotNull(message = "status is required")
    private FinanceStatusType status;

    private String note;
}
