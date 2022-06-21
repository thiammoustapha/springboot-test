package com.springboot.test.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryOfResidenceDateValidator implements ConstraintValidator<CountryOfResidence, String> {
    @Override
    public boolean isValid(final String valueToValidate, final ConstraintValidatorContext context) {
        return "FR".equals(valueToValidate);
    }
}