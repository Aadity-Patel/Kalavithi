package com.tw.prograd.user;


import com.tw.prograd.user.dto.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not present"));

        return userAuthRepository.findById(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not present"));

    }

    public UserProfile registerNewUserAccount(UserProfile userProfile) throws DataIntegrityViolationException {

        UserEntity userEntity = UserEntity.builder().email(userProfile.getEmail()).mobileNumber(userProfile.getMobileNumber()).build();

        UserEntity savedUser = userRepository.saveAndFlush(userEntity);
        String encodedPassword = passwordEncoder.encode(userProfile.getPassword());
        UserAuthEntity userAuth = UserAuthEntity.
                builder().id(savedUser.getId())
                .password(encodedPassword)
                .build();
        if (userAuthRepository.findById(savedUser.getId()).isPresent())
            throw new DataIntegrityViolationException("User already present");
        userAuthRepository.saveAndFlush(userAuth);

        return userProfile;
    }

    public Optional<UserEntity> getByUserEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public String getPasswordByUserName(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return userDetails.getPassword();
    }

    public ResponseEntity<String> updatePassword(String username, String newPassword) {
        UserAuthEntity userAuthEntity = (UserAuthEntity) loadUserByUsername(username);
        userAuthEntity.setPassword(passwordEncrypt(newPassword));
        userAuthRepository.save(userAuthEntity);
        return ResponseEntity.status(200).build();
    }

    public String passwordEncrypt(String password) {

        return passwordEncoder.encode(password);
    }
}
