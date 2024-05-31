package com.jibi.back_end.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api","/api/v1/**","/api/v1/cmi/**",
                                "/api/v1/cmi/user","/api/v1/cmi/user/**",
                                "/api/v1/cmi/account","/api/v1/cmi/account/**",
                                "/api/v1/cmi/transaction","/api/v1/cmi/transaction/**")
                        .permitAll()
                );
        return http.build();
    }
}
