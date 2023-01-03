package com.tw.prograd.user.dto;

import com.tw.prograd.user.validate.ValidEmail;
import com.tw.prograd.user.validate.ValidPassword;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@Setter
@Getter
public class UserCredentials {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String oldPassword;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String newPassword;
}


