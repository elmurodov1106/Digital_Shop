package com.example.digital_shop.config;

import com.example.digital_shop.filter.JwtTokenFilter;
import com.example.digital_shop.service.AuthService;
import com.example.digital_shop.service.AuthenticationService;
import com.example.digital_shop.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final AuthService authService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final String[] permitAll = {"/swagger-ui/**", "/v3/api-docs/**", "/auth/**", "/role/**","/user/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests((authorizer) -> {
                    authorizer
                            .requestMatchers(permitAll).permitAll()
                            .anyRequest().authenticated();

                })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore((new JwtTokenFilter(authenticationService,jwtService)),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }




    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
}
