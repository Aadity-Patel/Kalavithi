package com.tw.prograd.user;

import com.tw.prograd.user.dto.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Base64;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class UserProfileTest {
    private Validator validator;
    UserProfile userProfile;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        userProfile = new UserProfile();
    }

    @Test
    public void shouldReturnTrueWhenUserProfileCredentialsConstraintsAreSatisfied() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@12345").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserProfileCredentialsAreEmpty() {

        userProfile.setEmail("");
        userProfile.setPassword("");
        userProfile.setMobileNumber("");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenUserProfileCredentialsAreNull() {

        userProfile.setEmail(null);
        userProfile.setPassword(null);
        userProfile.setMobileNumber(null);

        assertThrows(ValidationException.class, () -> validator.validate(userProfile));
    }

    @Test
    public void shouldReturnFalseWhenUserProfileCredentialsConstraintsAreSatisfied1() {

        userProfile.setEmail("abc@gmail.");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@12345").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);

        assertThat(violations.size(), is(equalTo(1)));
    }

    @Test
    public void shouldReturnFalseWhenUserEmailIsInvalid() {

        userProfile.setEmail("abcgmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@12345").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserPasswordHasNoCapitalLetters() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("abc@12345").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserPasswordHasNoSmallLetters() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("ABC@12345").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserPasswordHasNoSpecialCharacters() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc12345").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserPasswordHasNoDigits() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@abcde").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserPasswordHasLessCharacterThanNeeded() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserPasswordExceedsCharacterLimit() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abcdef@@@@@123456").getBytes()));
        userProfile.setMobileNumber("6789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserMobileNumberIsLessThanTenDigits() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@12345").getBytes()));
        userProfile.setMobileNumber("67891234");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserMobileNumberIsGreaterThanTenDigits() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@12345").getBytes()));
        userProfile.setMobileNumber("678912345689");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenUserMobileNumberStartWithNumberLessThanFive() {

        userProfile.setEmail("abc@gmail.com");
        userProfile.setPassword(Base64.getEncoder().encodeToString(("Abc@12345").getBytes()));
        userProfile.setMobileNumber("1789123456");
        Set<ConstraintViolation<UserProfile>> violations = validator.validate(userProfile);
        assertFalse(violations.isEmpty());
    }
}