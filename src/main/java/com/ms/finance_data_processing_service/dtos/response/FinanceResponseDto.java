package com.ms.finance_data_processing_service.dtos.response;

import com.ms.finance_data_processing_service.entites.Types.FinanceCategoryType;
import com.ms.finance_data_processing_service.entites.Types.FinanceStatusType;
import com.ms.finance_data_processing_service.entites.Types.FinanceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceResponseDto {
    private Long id;

    private Long amount;

    private FinanceType type;

    private FinanceCategoryType category;

    private FinanceStatusType status;

    private String note;

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
