package com.pharmacy.modules.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println("{\"status\": " + HttpServletResponse.SC_UNAUTHORIZED + ", \"message\": \"" + "Invalid authentication details." + "\" }");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            log.error("This user doesn't have the necessary roles/permissions/privileges to access this resource.");
        }
        if(!authentication.isAuthenticated()){
            log.error("This user is not authenticated.");
        }
        authentication.setAuthenticated(false);
        log.info("Exception customaccessdenied: " + accessDeniedException.getMessage());

    }
}