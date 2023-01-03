package com.tw.prograd.user.validate;

import com.tw.prograd.user.validate.MobileNumberConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = MobileNumberConstraintValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidMobileNumber {
    String message() default "Invalid Mobile Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}