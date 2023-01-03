package com.tw.prograd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(GET, "/images/**").permitAll()
                .antMatchers(POST, "/users/register").permitAll()
                .antMatchers(GET, "/users/image-like-count/").permitAll()
                .anyRequest().authenticated().and().cors()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .csrf().disable();
    }
}
