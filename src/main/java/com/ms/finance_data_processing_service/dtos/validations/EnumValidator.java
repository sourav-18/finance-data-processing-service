package com.ms.finance_data_processing_service.dtos.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;
    private List<String> allowedValues;
    private String field;

    @Override
    public void initialize(ValidEnum annotation) {
        this.field=annotation.field();
        this.enumClass = annotation.enumClass();
        this.allowedValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // let @NotNull handle nulls

        boolean valid = allowedValues.contains(value);

        if (!valid) {
            String allowed   = String.join(", ", allowedValues);

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    field + " must be one of: " + allowed
            ).addConstraintViolation();
        }

        return valid;
    }

}
