package com.ms.finance_data_processing_service.services;

import com.ms.finance_data_processing_service.dtos.request.BalanceQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.request.CategoryAmountQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.response.BalanceByMonthResponseDto;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinanceRepository financeRepository;

    public BalanceResponseDto getBalance(BalanceQueryRequestDto query) {
        StartAndEndTimeUtil dateRange = ConstantUtil
                .getStatAndEndDateTime(query.getStartDate(), query.getEndDate());

        Long income = financeRepository.getTypeAmount(FinanceType.Income, dateRange.startDateTime(), dateRange.endDateTime());
        Long expense = financeRepository.getTypeAmount(FinanceType.Expense, dateRange.startDateTime(), dateRange.endDateTime());
        long netBalance = income - expense;
        Boolean isProfit = netBalance > 0;
        return BalanceResponseDto.builder()
                .income(income)
                .expense(expense)
                .netBalance(netBalance)
                .isProfit(isProfit).build();
    }

    public List<CategoryAmountResponseDto> getCategoryAmount(CategoryAmountQueryRequestDto query) {
        StartAndEndTimeUtil dateRange = ConstantUtil
                .getStatAndEndDateTime(query.getStartDate(), query.getEndDate());

        List<CategoryAmountResponseDto> categoryAmounts = financeRepository
                .getCategoryAmount(dateRange.startDateTime(), dateRange.endDateTime());

        Map<FinanceCategoryType, Long> amountMap = categoryAmounts.stream()
                .collect(Collectors.toMap(
                        CategoryAmountResponseDto::getCategory,
                        CategoryAmountResponseDto::getAmount
                ));

        return Arrays.stream(FinanceCategoryType.values())
                .map(category -> new CategoryAmountResponseDto(
                        amountMap.getOrDefault(category, 0L),
                        category
                )).toList();
    }

    public List<BalanceByMonthResponseDto> getBalanceByMonth(String year) {

        LocalDateTime currentYear=ConstantUtil.getStarYear(year);
        LocalDateTime nextYear=ConstantUtil.getEndYear(year);

        List<BalanceByMonthResponseDto> allMonthBalance=ConstantUtil.months.stream().map(BalanceByMonthResponseDto::new).toList();

        List<BalanceByMonthResponseDto> monthBalances= financeRepository
                .getBalanceByMonth(currentYear, nextYear);

        for (BalanceByMonthResponseDto monthBalance:monthBalances){
            String month= ConstantUtil.months.get(monthBalance.getMonthWithDateTime().getMonthValue()-1);

            Optional<BalanceByMonthResponseDto> monthBalanceFromAll= allMonthBalance.stream()
                    .filter((item)-> Objects.equals(item.getMonth(), month))
                    .findFirst();
            if(monthBalanceFromAll.isPresent()){
                monthBalanceFromAll.get().setIncome(monthBalance.getIncome());
                monthBalanceFromAll.get().setExpense(monthBalance.getExpense());
                monthBalanceFromAll.get().setNetBalance(monthBalance.getIncome()-monthBalance.getExpense());
            }
        }
        return allMonthBalance;
    }
}
