package com.ms.finance_data_processing_service.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.finance_data_processing_service.entites.Types.FinanceCategoryType;
import com.ms.finance_data_processing_service.entites.Types.FinanceStatusType;
import com.ms.finance_data_processing_service.entites.Types.FinanceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "finances",
        indexes = {
                @Index(name = "idx_finance_amount",columnList = "amount"),
                @Index(name = "idx_finance_type",columnList = "type"),
                @Index(name = "idx_finance_category",columnList = "category"),
                @Index(name = "idx_finance_status",columnList = "status"),
                @Index(name = "idx_finance_note",columnList = "note"),
                @Index(name = "idx_finance_is_deleted",columnList = "is_deleted")
        }
)
public class FinanceEntity extends BaseEntity{

    private Long amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinanceType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinanceCategoryType category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinanceStatusType status;

    private String note;

    @Column(name = "is_deleted",columnDefinition = "bool DEFAULT false",nullable = false)
    private boolean isDeleted=false;

    @JoinColumn(name = "created_by",nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    @JsonIgnore
    private UserEntity updatedBy;
}
