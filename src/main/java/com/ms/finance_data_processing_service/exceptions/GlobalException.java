package com.ms.finance_data_processing_service.exceptions;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ms.finance_data_processing_service.dtos.response.ApiResponseDto;
import com.ms.finance_data_processing_service.mappers.ApiResponseMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorsMessage = ex.getBindingResult().getFieldErrors().stream().findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseMapper.error(
                        HttpStatus.BAD_REQUEST.value(),
                        errorsMessage));
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleApiErrorException(ApiErrorException ex) {
        return ResponseEntity.status(ex.getError().getStatusCode())
                .body(ApiResponseMapper.error(
                        ex.getError().getStatusCode(),
                        ex.getError().getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleRootException(Exception ex) {
        System.out.println(ex.getMessage()); //todo add log
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseMapper.error(
                        500,
                        ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValidationException(ConstraintViolationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseMapper.error(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage()));
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ApiResponseDto<Void>> handleJsonParseException(HttpMessageNotReadableException ex) {
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponseMapper.error(
//                        HttpStatus.BAD_REQUEST.value(),
//                        ex.getMessage()));
//    }

}
