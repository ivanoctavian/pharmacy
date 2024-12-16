package com.pharmacy.modules.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private final CustomBasicAuthProvider customBasicAuthProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(customBasicAuthProvider.supports(authentication.getClass())){
            return customBasicAuthProvider.authenticate(authentication);
        }
        throw new BadCredentialsException("Invalid credentials.");

    }
}
