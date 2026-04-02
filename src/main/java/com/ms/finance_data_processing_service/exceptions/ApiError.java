package com.ms.finance_data_processing_service.exceptions;

import lombok.Getter;

@Getter
public enum ApiError {
    INVALID_EMAIL_PASSWORD(
            401,
            "Invalid email or password"
    ),

    USER_NOT_FOUND(
            404,
            "User not found"
    ),

    DATA_NOT_FOUND(
            404,
            "Data not found"
    ),

    TOKEN_MISSING(
            401,
            "Authentication token is missing or malformed"
    ),
    INACTIVE_USER(
            403,
            "User account is inactive"
    ),

    INVALID_ACCESS(
            403,
            "You do not have permission to perform this action."
    ),

    DUPLICATE_USER(
            400,
            "Email already exist"
    )

    ;
    private final int statusCode;
    private final String message;

    ApiError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    }
