package com.ms.finance_data_processing_service.dtos.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseApiResponseDto {
    private final boolean success;
    private final int statusCode;
    private final String message;
    private final LocalDateTime timestamp=LocalDateTime.now();

    public BaseApiResponseDto(boolean success, Integer statusCode, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }
}
