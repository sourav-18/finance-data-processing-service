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
@Table(name = "finances",indexes = {
        //todo
})
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

    @JoinColumn(nullable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserEntity updateBy;
}
