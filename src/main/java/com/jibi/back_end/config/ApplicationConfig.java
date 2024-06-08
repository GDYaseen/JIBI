package com.jibi.back_end.config;

import com.jibi.back_end.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // @Bean
    // public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    //     return config.getAuthenticationManager();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


@Service
@RequiredArgsConstructor
class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final SuperAdminRepository superAdminRepository;
    private final AgentRepository agentRepository;
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,BadCredentialsException {
        System.out.println("loadUserByUsername");
        UserDetails ud = userRepository.findByEmailOrPhoneNumber(username, username)
        .orElse(null);
        if(ud==null) ud = adminRepository.findByEmail(username).orElse(null);
        if(ud==null) ud = superAdminRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return ud;
    }
    public UserDetails loadUserByUsernameAndRole(String username, String role) throws UsernameNotFoundException {
        System.out.println("loadUserByUsernameAndRole");
        switch (role) {
            case "com.jibi.back_end.models.Admin":
                return adminRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            case "com.jibi.back_end.models.SuperAdmin":
                return superAdminRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            case "com.jibi.back_end.models.Agent":
                return agentRepository.findByEmailOrPhoneNumber(username, username)
                        .orElseThrow(() -> new UsernameNotFoundException("Agent not found"));
            case "com.jibi.back_end.models.Client":
                return clientRepository.findByEmailOrPhoneNumber(username, username)
                        .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
            default:
                return userRepository.findByEmailOrPhoneNumber(username, username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
    }
}

// class CustomerAuthManager implements AuthenticationManager{

//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
//     }

// }