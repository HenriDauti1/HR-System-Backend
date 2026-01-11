package com.hr_system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr_system.util.ApiResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());

                            String jsonResponse = new ObjectMapper().writeValueAsString(
                                    ApiResponseUtils.createError(
                                            "error",
                                            HttpStatus.UNAUTHORIZED.value(),
                                            "Authentication Required",
                                            "Please provide valid Basic Authentication credentials"
                                    )
                            );
                            response.getWriter().write(jsonResponse);
                        })
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.FORBIDDEN.value());

                            String jsonResponse = new ObjectMapper().writeValueAsString(
                                    ApiResponseUtils.createError(
                                            "error",
                                            HttpStatus.FORBIDDEN.value(),
                                            "Access Denied",
                                            "You do not have permission to access this resource"
                                    )
                            );
                            response.getWriter().write(jsonResponse);
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin@hrms.com")
                .password(passwordEncoder().encode("admin123"))
                .roles("CRUD", "ADMIN")
                .build();

        UserDetails coordinator = User.builder()
                .username("coordinator@hrms.com")
                .password(passwordEncoder().encode("coordinator123"))
                .roles("READONLY", "COORDINATOR")
                .build();

        return new InMemoryUserDetailsManager(admin, coordinator);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}