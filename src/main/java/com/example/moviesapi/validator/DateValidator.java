package com.example.moviesapi.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator implements ConstraintValidator<ValidDate, Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        if (date == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        try {
            Date parsedDate = sdf.parse(sdf.format(date));
            return parsedDate.before(new Date());
        } catch (ParseException e) {
            return false;
        }
    }
}