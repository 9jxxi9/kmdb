package com.example.moviesapi.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class ReleaseYearValidator implements ConstraintValidator<ValidReleaseYear, Year> {

    @Override
    public boolean isValid(Year releaseYear, ConstraintValidatorContext context) {
        if (releaseYear == null) {
            return false;
        }

        Year currentYear = Year.now();
        return !releaseYear.isAfter(currentYear);
    }
}