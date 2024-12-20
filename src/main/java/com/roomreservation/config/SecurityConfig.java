package com.roomreservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable() // Disable CSRF for testing purposes
      .authorizeHttpRequests(authorize -> authorize
        .anyRequest().permitAll() // Allow all requests without authentication
      );
    http.headers().frameOptions().disable();
    return http.build();
  }
}