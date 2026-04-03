package com.ms.finance_data_processing_service.dtos.response;

import com.ms.finance_data_processing_service.entites.Types.FinanceCategoryType;

public record CategoryAmountResponseDto(Long amount, FinanceCategoryType category) {
}
