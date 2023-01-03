package com.tw.prograd.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.prograd.dto.ErrorResponse;
import com.tw.prograd.user.dto.User;
import com.tw.prograd.user.dto.UserCredentials;
import com.tw.prograd.user.dto.UserProfile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private static String validAuthorizationToken;
    private static String invalidAuthorizationToken;
    private final String userName = "admin@test.com";
    private final String password = "password";
    private final String mobileNumber = "6789123456";
    private final String newUserName = "user13@test.com";
    private final String newPassword = "P@ssword123";
    private final String newMobileNumber = "9234567890";
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserAuthService userAuthService;

    private UserProfile newUserProfile;

    private User user;

    private Validator validator;

    @BeforeEach
    void setUp() {

        UserEntity userEntity = UserEntity.builder().email(userName).mobileNumber(mobileNumber).build();
        UserEntity savedUser = userRepository.saveAndFlush(userEntity);

        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(savedUser.getId())
                .user(savedUser)
                .password(passwordEncoder.encode(password)).build();
        userAuthRepository.saveAndFlush(userAuthEntity);


        validAuthorizationToken = Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
        invalidAuthorizationToken = Base64.getEncoder().encodeToString(("user@unknown.com" + ":" + password).getBytes());


        user = User.builder().id(savedUser.getId()).username(userName).build();

        newUserProfile = UserProfile.builder()
                .email(newUserName)
                .mobileNumber(newMobileNumber)
                .password(Base64.getEncoder().encodeToString(newPassword.getBytes())).build();

    }

    @AfterEach
    void tearDown() {
        userAuthRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToLoginWithValidCredential() throws Exception {

        mvc.perform(get("/users/login")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(user)));
    }

    @Test
    void shouldNotBeAbleToLoginWithoutValidCredential() throws Exception {
        ErrorResponse error = ErrorResponse.builder().errorCode(UNAUTHORIZED.value()).message(UNAUTHORIZED.getReasonPhrase()).build();

        mvc.perform(get("/users/login")
                        .header("Authorization", "Basic " + invalidAuthorizationToken))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(mapper.writeValueAsString(error)));
    }

    @Test
    void shouldCheckWhetherTheOldPasswordIsCorrect() throws Exception {
        UserCredentials userCredentials = UserCredentials.builder().
                username(userName).
                oldPassword(password).
                newPassword(newPassword).
                build();
        String expectedPassword = userAuthService.getPasswordByUserName(userName);
        assertTrue(passwordEncoder.matches(userCredentials.getOldPassword(), expectedPassword));
    }

    @Test
    void shouldNotBeAbleToChangePasswordWhenNewAndOldPasswordAreSame() throws Exception {
        UserCredentials user = UserCredentials.builder()
                .username(userName)
                .oldPassword(password)
                .newPassword(password)
                .build();

        mvc.perform(post("/users/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Nested
    public class UserRegistration {

        @Test
        void shouldBeAbleToRegisterWithValidCredentials() throws Exception {

            mvc.perform(post("/users/register").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(newUserProfile))).andExpect(status().isOk());

        }

        @Test
        void shouldNotAllowExistingUserToRegisterAgain() throws Exception {

            mvc.perform(post("/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(newUserProfile)))
                    .andExpect(status().isOk());
            mvc.perform(post("/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(newUserProfile)))
                    .andExpect(status().isConflict());
        }
    }
}
