package com.ms.finance_data_processing_service.mappers;

import com.ms.finance_data_processing_service.dtos.request.FinanceCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.FinanceResponseDto;
import com.ms.finance_data_processing_service.entites.FinanceEntity;

public class FinanceMapper {

    public static FinanceEntity toEntity(FinanceCreateRequestDto body){
        FinanceEntity finance=new FinanceEntity();
        finance.setAmount(body.getAmount());
        finance.setType(body.getType());
        finance.setCategory(body.getCategory());
        finance.setStatus(body.getStatus());
        finance.setNote(body.getNote());
        return finance;
    }

    public static FinanceResponseDto toDto(FinanceEntity finance){
        return FinanceResponseDto.builder()
                .id(finance.getId())
                .amount(finance.getAmount())
                .type(finance.getType())
                .status(finance.getStatus())
                .category(finance.getCategory())
                .note(finance.getNote()==null?null:finance.getNote().trim())
                .createdBy(finance.getCreatedBy().getName())
                .updatedBy(finance.getUpdateBy()==null?null:finance.getUpdateBy().getName())
                .createdAt(finance.getCreatedAt())
                .createdAt(finance.getUpdatedAt())
                .build();
    }



}
