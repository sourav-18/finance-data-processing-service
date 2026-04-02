package com.ms.finance_data_processing_service.exceptions;

import lombok.Getter;

@Getter
public class ApiErrorException extends RuntimeException{
    private final ApiError error;

    public ApiErrorException(ApiError error) {
        super(error.getMessage());
        this.error = error;
    }
}
