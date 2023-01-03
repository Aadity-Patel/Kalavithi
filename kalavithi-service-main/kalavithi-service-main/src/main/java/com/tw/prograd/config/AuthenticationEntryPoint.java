package com.tw.prograd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.prograd.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.WWW_AUTHENTICATE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException {
        response.addHeader(CONTENT_TYPE, APPLICATION_JSON.toString());
        response.addHeader(WWW_AUTHENTICATE, "Basic realm=" + getRealmName());
        response.setStatus(UNAUTHORIZED.value());

        PrintWriter writer = response.getWriter();

        ErrorResponse error = ErrorResponse.builder()
                .errorCode(UNAUTHORIZED.value())
                .message(UNAUTHORIZED.getReasonPhrase()).build();
        writer.print(mapper.writeValueAsString(error));
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("kalavithi");
        super.afterPropertiesSet();
    }

}
