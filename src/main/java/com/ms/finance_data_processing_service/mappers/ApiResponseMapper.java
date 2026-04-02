package com.ms.finance_data_processing_service.mappers;

import com.ms.finance_data_processing_service.dtos.response.ApiPageResponseDto;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class ApiResponseMapper {
    public static <T> ApiResponseDto<T> success(int statusCode, String message, T data) {
        return new ApiResponseDto<>(true, statusCode, message, data);
    }

    public static ApiResponseDto<Void> error(int statusCode, String message) {
        return new ApiResponseDto<>(false, statusCode, message, null);
    }

    public static <T> ApiPageResponseDto<T> successWithPagination(int statusCode, String message, List<T> data, int page, int size, int totalPageCount, long totalCount) {
        return new ApiPageResponseDto<>(true, statusCode, message, data,page,size,totalPageCount,totalCount);
    }


    public static String securityError(int statusCode, String message) {
        return String.format("{\"success\":false,\"statusCode\":%d,\"message\":\"%s\",\"data\":null}",
                statusCode, message);
    }

}
