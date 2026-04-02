package com.ms.finance_data_processing_service.dtos.response;

import lombok.Getter;

@Getter
public class ApiResponseDto<T> extends BaseApiResponseDto {
    private final T data;

    public ApiResponseDto(boolean success, int statusCode, String message, T data) {
        super(success, statusCode, message);
        this.data=data;
    }
}
