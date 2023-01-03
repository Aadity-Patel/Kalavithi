package com.tw.prograd.user;

import com.tw.prograd.user.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    private final String userName = "admin@test.com";
    private final String password = "password";
    private final String nonExistingUserName = "abc@test.com";
    private final String mobileNumber = "9561345621";
    UserEntity userEntity;
    UserAuthEntity userAuthEntity;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserAuthService userAuthService;
    @Mock
    private UserAuthRepository userAuthRepository;
    @BeforeEach
    void setUp() {

        userEntity = UserEntity.builder().email(userName).mobileNumber(mobileNumber).build();
        userAuthEntity = UserAuthEntity.builder()
                .id(userEntity.getId())
                .user(userEntity)
                .password(password)
                .build();
    }


    @Test
    void shouldBeAbleToFindUserWithValidEmail() {
        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> actualDisplayList = userAuthService.getByUserEmail(userName);
        Optional<UserEntity> expectedViewList = Optional.of(userEntity);

        assertThat(expectedViewList, is(equalTo(actualDisplayList)));

        verify(userRepository, times(1)).findByEmail(userName);
    }

    @Test
    void shouldReturnEmptyWhenEmailIsNotFound() {
        when(userRepository.findByEmail(nonExistingUserName)).thenReturn(Optional.empty());

        Optional<UserEntity> actualDisplayList = userAuthService.getByUserEmail(nonExistingUserName);
        Optional<UserEntity> expectedViewList = Optional.empty();

        assertThat(expectedViewList, is(equalTo(actualDisplayList)));

        verify(userRepository, times(1)).findByEmail(nonExistingUserName);

    }

    @Test
    void shouldReturnPasswordWhenUserNameIsGiven(){
        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(userEntity));

        when(userAuthRepository.findById(userAuthEntity.getId())).thenReturn(Optional.of(userAuthEntity));
        String actualPassword = userAuthService.getPasswordByUserName(userName);
        String expectedPassword = "password";

        assertThat(expectedPassword,is(equalTo(actualPassword)));
        verify(userRepository,times(1)).findByEmail(userName);

    }
    @Test
    void shouldNotReturnPasswordWhenInvalidUserNameIsGiven()throws Exception{
        when(userRepository.findByEmail(nonExistingUserName)).thenReturn(Optional.empty());
         assertThrows(UsernameNotFoundException.class,()->userAuthService.getPasswordByUserName(nonExistingUserName));
        verify(userRepository,times(1)).findByEmail(nonExistingUserName);

    }



}
