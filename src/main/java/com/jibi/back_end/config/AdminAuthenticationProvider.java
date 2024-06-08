package com.jibi.back_end.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jibi.back_end.repos.AdminRepository;
import com.jibi.back_end.repos.SuperAdminRepository;
import com.jibi.back_end.repos.UserRepository;

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        System.out.println("inside adminAuthenticationProvider, " + username + " " + password);

        UserDetails userDetails = userRepository.findByEmailOrPhoneNumber(username, username).orElse(null);
        if (userDetails == null) {
            userDetails = adminRepository.findByEmail(username).orElse(null);
            if (userDetails == null) {
                userDetails = superAdminRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("password is wrong");
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
