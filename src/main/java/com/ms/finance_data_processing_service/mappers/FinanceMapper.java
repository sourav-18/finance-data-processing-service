package com.ms.finance_data_processing_service.mappers;

import com.ms.finance_data_processing_service.dtos.request.FinanceCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.request.FinanceUpdateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.FinanceResponseDto;
import com.ms.finance_data_processing_service.entites.FinanceEntity;

public class FinanceMapper {

    public static FinanceEntity toEntity(FinanceCreateRequestDto body){
        FinanceEntity finance=new FinanceEntity();
        finance.setAmount(body.getAmount());
        finance.setType(body.getType());
        finance.setCategory(body.getCategory());
        finance.setStatus(body.getStatus());
        finance.setNote(body.getNote()==null?null:body.getNote().trim());
        return finance;
    }

    public static FinanceResponseDto toDto(FinanceEntity finance){
        return FinanceResponseDto.builder()
                .id(finance.getId())
                .amount(finance.getAmount())
                .type(finance.getType())
                .status(finance.getStatus())
                .category(finance.getCategory())
                .note(finance.getNote())
                .createdBy(finance.getCreatedBy().getName())
                .updatedBy(finance.getUpdatedBy()==null?null:finance.getUpdatedBy().getName())
                .createdAt(finance.getCreatedAt())
                .createdAt(finance.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FinanceEntity finance, FinanceUpdateRequestDto body){
        finance.setAmount(body.getAmount());
        finance.setType(body.getType());
        finance.setCategory(body.getCategory());
        finance.setStatus(body.getStatus());
        finance.setNote(body.getNote()==null?null:body.getNote().trim());
    }



}
