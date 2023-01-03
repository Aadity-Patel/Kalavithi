package com.tw.prograd.user;

import com.tw.prograd.user.dto.User;
import com.tw.prograd.user.dto.UserCredentials;
import com.tw.prograd.user.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Base64;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserAuthService userAuthService;

    @GetMapping("/login")
    public User login(Authentication authentication) {

        UserAuthEntity user = (UserAuthEntity) authentication.getPrincipal();

        return User.builder()
                .id(user.getId())
                .username(user.getUsername()).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid UserCredentials userCredentials) {
        String oldPassword = userCredentials.getOldPassword();
        String newPassword = userCredentials.getNewPassword();

        String actualPassword = userAuthService.getPasswordByUserName(userCredentials.getUsername());
        if (!(userAuthService.passwordEncoder.matches(oldPassword, actualPassword)) || oldPassword.equals(newPassword)) {
            return ResponseEntity.status(400).build();
        }

        return userAuthService.updatePassword(userCredentials.getUsername(), newPassword);
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfile> register(@RequestBody @Valid UserProfile userProfile) throws Exception {
        try {
            return new ResponseEntity<UserProfile>(userAuthService.registerNewUserAccount(userProfile), HttpStatus.OK);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username Already Exist");
        }
    }

    @GetMapping(value = "/logout")
    public void logout() {

    }
}
