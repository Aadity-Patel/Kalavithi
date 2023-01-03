package com.tw.prograd.user.config;

import com.tw.prograd.user.UserAuthEntity;
import com.tw.prograd.user.UserAuthRepository;
import com.tw.prograd.user.UserEntity;
import com.tw.prograd.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner userInit(UserRepository userRepository, UserAuthRepository userAuthRepository) {
        return (args) -> {

            String userName = "admin@kalavithi.com";
            String password = "password";
            String mobileNumber = "1234567890";
            UserEntity userEntity = UserEntity.builder().email(userName).mobileNumber(mobileNumber).build();
            if (userRepository.findByEmail(userName).isPresent()) return;

            UserEntity savedUser = userRepository.save(userEntity);

            UserAuthEntity userAuth = UserAuthEntity.builder().id(savedUser.getId()).password(password).build();
            userAuthRepository.save(userAuth);
        };

    }
}
