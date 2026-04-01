package com.ms.finance_data_processing_service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex){
        String errorsMessage = ex.getBindingResult().getFieldErrors().stream().findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("validation error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleDAccountNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

}
