package com.medilabo.clientservice.validation.validator;


import com.medilabo.clientservice.validation.annotation.ValidPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    private static final String PHONE_PATTERN = "^[0-9-]+$";

    @Override
    public void initialize(ValidPhone constraintAnnotation) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Champ facultatif
        return value.length() >= 10 && value.length() <= 15 && value.matches(PHONE_PATTERN);
    }
}

