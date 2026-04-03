package com.ms.finance_data_processing_service.dtos.response;

import com.ms.finance_data_processing_service.entites.Types.FinanceCategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAmountResponseDto {
    private Long amount;
    private FinanceCategoryType category;

    public CategoryAmountResponseDto(Long amount, FinanceCategoryType category) {
        this.amount = amount;
        this.category = category;
    }
}
