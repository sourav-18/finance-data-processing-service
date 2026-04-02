package com.ms.finance_data_processing_service.services;

import com.ms.finance_data_processing_service.dtos.request.FinanceCreateRequestDto;
import com.ms.finance_data_processing_service.dtos.response.FinanceResponseDto;
import com.ms.finance_data_processing_service.entites.FinanceEntity;
import com.ms.finance_data_processing_service.mappers.FinanceMapper;
import com.ms.finance_data_processing_service.repositories.FinanceRepository;
import com.ms.finance_data_processing_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financeRepository;
    private final UserRepository userRepository;

    public FinanceResponseDto create(FinanceCreateRequestDto financeCreateBody,Long loggedInUserId){
        FinanceEntity finance= FinanceMapper.toEntity(financeCreateBody);
        finance.setCreatedBy(userRepository.getReferenceById(loggedInUserId));
        financeRepository.save(finance);
        return FinanceMapper.toDto(finance);
    }

}
