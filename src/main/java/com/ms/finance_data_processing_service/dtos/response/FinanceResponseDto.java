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

    public FinanceResponseDto(Long id,
                              Long amount,
                              FinanceType type,
                              FinanceStatusType status,
                              FinanceCategoryType category,
                              String note,
                              String createdBy,
                              String updatedBy,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.status = status;
        this.note = note;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
