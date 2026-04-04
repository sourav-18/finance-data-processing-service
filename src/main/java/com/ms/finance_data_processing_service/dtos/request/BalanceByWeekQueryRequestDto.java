package com.ms.finance_data_processing_service.dtos.request;

import com.ms.finance_data_processing_service.dtos.validations.ValidEnum;
import com.ms.finance_data_processing_service.entites.Types.FinanceStatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceByWeekQueryRequestDto extends DateUnitRequestDto{
    @ValidEnum(enumClass = FinanceStatusType.class,field = "Status")
    private String status;
}
