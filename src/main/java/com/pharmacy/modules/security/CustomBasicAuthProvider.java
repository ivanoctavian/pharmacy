package com.pharmacy.modules.security;

import com.pharmacy.modules.security.model.User;
import com.pharmacy.modules.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
@Component
@Slf4j
public class CustomBasicAuthProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        Optional<User> userOpt = userRepository.findByUsernameAndPassword(username, password);
        log.info("Username = {} & password = {}", username, password);
        if(userOpt.isEmpty()){
            log.error("Username is incorrect.");
            throw new BadCredentialsException("Username is incorrect.");
        }
        User user = userOpt.get();
        if(!user.isEnabled()){
            log.error("User is not enabled.");
            throw new BadCredentialsException("User is not enabled.");
        }
        if(!password.equals(user.getPassword())){
            log.error("Password is incorrect.");
            throw new BadCredentialsException("Password is incorrect.");
        }
        return new UsernamePasswordAuthenticationToken(username, password, Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
