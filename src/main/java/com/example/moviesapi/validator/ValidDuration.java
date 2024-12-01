package com.example.moviesapi.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DurationValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDuration {
    String message() default "Duration must be in the format HH:mm:ss";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
