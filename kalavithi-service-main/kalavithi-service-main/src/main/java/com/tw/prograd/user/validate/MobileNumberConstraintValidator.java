package com.tw.prograd.user.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileNumberConstraintValidator implements ConstraintValidator<ValidMobileNumber, String> {
    @Override
    public void initialize(ValidMobileNumber contactNumber) {
    }

    @Override
    public boolean isValid(String mobileNumber,
                           ConstraintValidatorContext cxt) {
        return mobileNumber != null && mobileNumber.matches("^[6-9]\\d{9}$")
                && (mobileNumber.length() == 10);
    }
}