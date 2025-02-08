package com.medilabo.clientservice.validation.annotation;

import com.medilabo.clientservice.validation.validator.AddressValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = AddressValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAddress {

    String message() default "L'adresse doit contenir entre 5 et 150 caractères.";

    int min() default 5;

    int max() default 150;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

