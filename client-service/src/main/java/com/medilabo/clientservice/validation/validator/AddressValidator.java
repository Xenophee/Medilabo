package com.medilabo.clientservice.validation.validator;

import com.medilabo.clientservice.validation.annotation.ValidAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

    private int min;
    private int max;

    @Override
    public void initialize(ValidAddress constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si l'adresse est vide ou null, on considère que c'est valide (facultatif)
        if (value == null || value.isBlank()) return true;

        // Vérification de la taille
        return value.length() >= min && value.length() <= max;
    }
}

