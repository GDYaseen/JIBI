package com.jibi.back_end.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.jibi.back_end.models.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AdminAuthenticationProvider adminAuthenticationProvider;

        // @Autowired
        //     private CustomUserDetailsService userDetailsService;

    @SuppressWarnings("removal")
@Primary
    @Bean
    public AuthenticationManager securityAuthenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).parentAuthenticationManager(null)
                .authenticationProvider(adminAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/api/auth","/api/auth/**","/api/auth/superadmin/login","/api/auth/admin/login")
                                .permitAll()
                                .requestMatchers("/api/v1/superadmin","/api/v1/superadmin/**","/api/v1/admin/create")
                                .hasAuthority(SuperAdmin.class.getName())
                                .requestMatchers("/api/v1/admin","/api/v1/admin/**","/api/v1/admin/modify/**")
                                .hasAuthority(SuperAdmin.class.getName())
                                .requestMatchers("/api/v1/agent","/api/v1/agent/create","/api/v1/agent/modify/**")
                                .hasAnyAuthority(Admin.class.getName(),SuperAdmin.class.getName())
                                .requestMatchers(HttpMethod.GET,"/api/v1/cmi/creancier/**")
                                .hasAuthority(Client.class.getName())
                                .requestMatchers("/api/v1/cmi/creancier","/api/v1/cmi/creancier/**") // Use antMatchers for more granular control
                                .hasAnyAuthority(Admin.class.getName(),SuperAdmin.class.getName())
                                .requestMatchers("/api/v1/agent/changepass/**") // Use antMatchers for more granular control
                                .hasAnyAuthority(Agent.class.getName(),Admin.class.getName(),SuperAdmin.class.getName())
                                .requestMatchers("/api/v1/client","/api/v1/client/create","/api/v1/client/modify/**")
                                .hasAnyAuthority(Agent.class.getName(),Admin.class.getName(),SuperAdmin.class.getName())
                                .requestMatchers("/api/v1/client/changepass/**") // Use antMatchers for more granular control
                                .hasAnyAuthority(Client.class.getName(),Agent.class.getName(),Admin.class.getName(),SuperAdmin.class.getName())
                                .anyRequest()
                        .permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(adminAuthenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowedHeaders(List.of("*"));
            return configuration;
        }));
        return http.build();
    }
@Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(null);  // This line prevents the infinite loop issue
    }
    // @Bean
    // public AuthenticationFailureHandler customAuthenticationFailureHandler() {
    //     return (request, response, exception) -> {
    //         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //         response.getWriter().write("Authentication failed: " + exception.getMessage());
    //     };
    // }
}
