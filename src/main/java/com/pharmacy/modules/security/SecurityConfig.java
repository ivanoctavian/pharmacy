package com.pharmacy.modules.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomBasicAuthProvider customBasicAuthProvider;
    private final CustomAccessDeniedHandler handler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    @Bean
    SecurityFilterChain securityFilterChain1(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/medicines/**").hasAnyRole("ADMIN", "EMPLOYEE");
                    auth.requestMatchers("/suppliers/**").permitAll();
                    auth.requestMatchers("/h2-console/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .authenticationProvider(customBasicAuthProvider)
                .authenticationManager(customAuthenticationManager)
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(exc -> {
                    exc.accessDeniedHandler(handler);
                    exc.authenticationEntryPoint(authenticationEntryPoint);
                })
                .cors(Customizer.withDefaults())
                //below is used for having access to h2 console.
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        .disable())
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
