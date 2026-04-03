package com.ms.finance_data_processing_service.services;

import com.ms.finance_data_processing_service.dtos.request.BalanceQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.response.BalanceResponseDto;
import com.ms.finance_data_processing_service.dtos.response.CategoryAmountResponseDto;
import com.ms.finance_data_processing_service.entites.Types.FinanceCategoryType;
import com.ms.finance_data_processing_service.entites.Types.FinanceType;
import com.ms.finance_data_processing_service.repositories.FinanceRepository;
import com.ms.finance_data_processing_service.utils.ConstantUtil;
import com.ms.finance_data_processing_service.utils.StartAndEndTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinanceRepository financeRepository;

    public BalanceResponseDto getBalance(BalanceQueryRequestDto query) {
        StartAndEndTimeUtil dateRange= ConstantUtil
                .getStatAndEndDateTime(query.getStartDate(),query.getEndDate());

        Long income = financeRepository.getTypeAmount(FinanceType.Income,dateRange.startDateTime(),dateRange.endDateTime());
        Long expense = financeRepository.getTypeAmount(FinanceType.Expense,dateRange.startDateTime(),dateRange.endDateTime());
        long netBalance = income - expense;
        Boolean isProfit = netBalance > 0;
        return BalanceResponseDto.builder()
                .income(income)
                .expense(expense)
                .netBalance(netBalance)
                .isProfit(isProfit).build();
    }

    public CategoryAmountResponseDto getBalanceByCategory(BalanceQueryRequestDto query) {
//        StartAndEndTimeUtil dateRange= ConstantUtil
//                .getStatAndEndDateTime(query.getStartDate(),query.getEndDate());
//        List<CategoryAmountResponseDto> categoryAmounts=financeRepository
//                .getCategoryAmount(dateRange.startDateTime(),dateRange.endDateTime());
//        Stream.of(FinanceCategoryType.values()).map((category)->{
//            Optional<CategoryAmountResponseDto> existAmount = categoryAmounts.stream().filter((amount) -> amount.category() == category).findFirst();
//            CategoryAmountResponseDto categoryAmount=new CategoryAmountResponseDto(0L,category);
//            if(existAmount.isPresent()){
//                categoryAmount.category(1);
//            }
//        })
        return null;
    }
}
