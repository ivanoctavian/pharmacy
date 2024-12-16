package com.pharmacy.modules.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomBasicAuthProvider customBasicAuthProvider;
    @Bean
    SecurityFilterChain securityFilterChain1(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/medicines").hasRole("EMPLOYEE");
                    auth.requestMatchers("/suppliers").permitAll();
                    auth.requestMatchers("/h2-console").permitAll();
                    auth.anyRequest().authenticated();
                })
                .authenticationProvider(customBasicAuthProvider)
                .authenticationManager(customAuthenticationManager)
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return httpSecurity.build();
    }
}
