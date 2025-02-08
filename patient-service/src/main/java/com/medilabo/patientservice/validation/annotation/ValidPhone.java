package com.medilabo.patientservice.validation.annotation;

import com.medilabo.patientservice.validation.validator.PhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {

    String message() default "Le numéro de téléphone doit contenir entre 10 et 15 caractères et ne doit contenir que des chiffres et des tirets.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
