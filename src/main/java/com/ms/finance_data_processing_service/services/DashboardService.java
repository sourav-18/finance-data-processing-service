package com.ms.finance_data_processing_service.services;

import com.ms.finance_data_processing_service.dtos.request.BalanceQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.request.CategoryAmountQueryRequestDto;
import com.ms.finance_data_processing_service.dtos.request.DateUnitRequestDto;
import com.ms.finance_data_processing_service.dtos.response.BalanceByMonthResponseDto;
import com.ms.finance_data_processing_service.dtos.response.BalanceByWeekResponseDto;
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

    public List<BalanceByMonthResponseDto> getBalanceByMonth(DateUnitRequestDto DateUnit) {

        LocalDateTime currentYear = ConstantUtil.getStarYear(DateUnit.getIntegerYear());
        LocalDateTime nextYear = ConstantUtil.getEndYear(DateUnit.getIntegerYear());

        List<BalanceByMonthResponseDto> allMonthBalance = ConstantUtil.months.stream().map(BalanceByMonthResponseDto::new).toList();

        List<BalanceByMonthResponseDto> monthBalances = financeRepository
                .getBalanceByMonth(currentYear, nextYear);

        for (BalanceByMonthResponseDto monthBalance : monthBalances) {
            int monthIndex = monthBalance.getDateTime().getMonthValue() - 1;
            if (monthIndex >= 0 && monthIndex <= 11) {
                BalanceByMonthResponseDto monthBalanceFromAll = allMonthBalance.get(monthIndex);
                monthBalanceFromAll.setIncome(monthBalance.getIncome());
                monthBalanceFromAll.setExpense(monthBalance.getExpense());
                monthBalanceFromAll.setNetBalance(monthBalance.getIncome() - monthBalance.getExpense());
            }
        }

        return allMonthBalance;
    }

    public List<BalanceByWeekResponseDto> getBalanceByWeek(DateUnitRequestDto DateUnit) {

        List<BalanceByWeekResponseDto> allWeeks = getAllWeeks(DateUnit.getIntegerYear(), DateUnit.getIntegerMonth());
        for (BalanceByWeekResponseDto week : allWeeks) {
            BalanceByWeekResponseDto balance = financeRepository.getBalanceByWeek(week.getFrom(), week.getTo());
            week.setIncome(balance.getIncome() == null ? 0L : balance.getIncome());
            week.setExpense(balance.getExpense() == null ? 0L : balance.getExpense());
            week.setNetBalance(week.getIncome() - week.getExpense());
            week.setTo(week.getTo().plusDays(-1));
        }

        return allWeeks;
    }

    private List<BalanceByWeekResponseDto> getAllWeeks(Integer year, Integer month) {
        LocalDateTime dateTime = ConstantUtil.getStarMonth(year, month);
        int weekIndex = 0;
        List<BalanceByWeekResponseDto> allWeeks = new LinkedList<>();
        while (dateTime.getMonthValue() == month) {
            LocalDateTime nextWeek = dateTime.plusDays(7);
            if (nextWeek.getMonthValue() > month) {
                nextWeek = nextWeek.withDayOfMonth(1);
            }
            allWeeks.add(new BalanceByWeekResponseDto
                    (ConstantUtil.weeks.get(weekIndex++), dateTime, nextWeek));
            dateTime = nextWeek;
        }
        return allWeeks;
    }
}
