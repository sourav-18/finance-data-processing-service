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
        return new StartAndEndTimeUtil(startDateTime,endDateTime);
    }
}
