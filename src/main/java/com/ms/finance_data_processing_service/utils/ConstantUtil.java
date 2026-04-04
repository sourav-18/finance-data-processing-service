package com.ms.finance_data_processing_service.utils;

import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ConstantUtil {
    public static final String REQUEST_USER_ID = "user-id";
    public static final long MAX_FINANCE_AMOUNT = 100000000L;
    private static final List<String> sortOrders = List.of("asc", "desc");

    public static List<Sort.Order> getSortWithOrder(Map<String, String> allowSortableFields, String rawSort) {
        if (rawSort == null || rawSort.isEmpty()) return null;
        return Arrays.stream(rawSort.split(","))
                .map(String::trim)
                .filter(item -> item.contains(":"))
                .map(item -> item.split(":"))
                .filter(arr -> arr.length == 2)
                .filter(arr -> allowSortableFields.containsKey(arr[0])
                        && sortOrders.contains(arr[1].toLowerCase()))
                .map(arr ->
                        new Sort.Order(
                                Sort.Direction.fromString(arr[1]),
                                allowSortableFields.get(arr[0])
                        )
                )
                .toList();
    }

    public static StartAndEndTimeUtil getStatAndEndDateTime(LocalDate starDate, LocalDate endDate) {
        LocalDateTime startDateTime = starDate == null ? null : starDate.atStartOfDay();
        LocalDateTime endDateTime = endDate == null ? null : endDate.plusDays(1).atStartOfDay().plusSeconds(-1);
        return new StartAndEndTimeUtil(startDateTime, endDateTime);
    }

    public final static List<String> months = List.of("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");

    public final static List<String> weeks = List.of("Week-1", "Week-2", "Week-3", "Week-4", "Week-5", "Week-6", "Week-7");

    public static LocalDateTime getStarYear(Integer inputYear) {
        int year = inputYear == null ? LocalDateTime.now().getYear() :inputYear;
        return LocalDateTime.of(year, 1, 1, 0, 0, 0, 0);
    }

    public static LocalDateTime getEndYear(Integer inputYear) {
        int year = inputYear == null ? LocalDateTime.now().getYear()+1 :inputYear+1;
        return LocalDateTime.of(year, 1, 1, 0, 0, 0, 0);
    }
    public static LocalDateTime getStarMonth(Integer inputYear,Integer inputMonth) {
        int year = inputYear == null ? LocalDateTime.now().getYear() :inputYear;
        int month = inputMonth == null ? LocalDateTime.now().getMonthValue() :inputMonth;
        return LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
    }
//    public static LocalDateTime getEndMonth(Integer inputYear,Integer inputMonth) {
//        int year = inputYear == null ? LocalDateTime.now().getYear() :inputYear;
//        int month = inputMonth == null ? LocalDateTime.now().getMonthValue()+1 :inputMonth+1;
//        return LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
//    }

}
