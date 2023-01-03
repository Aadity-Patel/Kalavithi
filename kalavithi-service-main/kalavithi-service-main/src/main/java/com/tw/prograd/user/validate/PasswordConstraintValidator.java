package com.tw.prograd.user.validate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$";

    @Override
    public void initialize(final ValidPassword password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context){
        return (validatePassword(password));
    }
    private boolean validatePassword(String password) {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        password = new String(decodedBytes);

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
