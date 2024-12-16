package com.pharmacy.modules.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        log.info("Exception authenticationEntryPoint: " + authException.getMessage());
        response.getOutputStream().println("{\"status\": " + HttpServletResponse.SC_UNAUTHORIZED + ", \"message\": \"" + "Invalid authentication details." + "\" }");
    }
    @Override
    public void afterPropertiesSet() {
        setRealmName("api-service");
        super.afterPropertiesSet();
    }
}