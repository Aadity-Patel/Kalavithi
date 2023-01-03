package com.tw.prograd.user.dto;

import com.tw.prograd.user.validate.ValidEmail;
import com.tw.prograd.user.validate.ValidMobileNumber;
import com.tw.prograd.user.validate.ValidPassword;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UserProfile {

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @ValidPassword
    @NotNull
    @NotEmpty
    private String password;

    @ValidMobileNumber
    @NotNull
    @NotEmpty
    private String mobileNumber;
}