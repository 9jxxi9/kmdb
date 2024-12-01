package com.example.moviesapi.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.sql.Time;

public class DurationValidator implements ConstraintValidator<ValidDuration, Time> {

    @Override
    public boolean isValid(Time duration, ConstraintValidatorContext context) {
        if (duration == null) {
            return false;
        }

        return true;
    }
}