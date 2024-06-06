package com.jibi.back_end.services;

import com.jibi.back_end.Controllers.auth.AdminAuthResponse;
import com.jibi.back_end.Controllers.auth.AuthenticationRequest;
import com.jibi.back_end.Controllers.auth.AuthenticationResponse;
import com.jibi.back_end.models.Admin;
import com.jibi.back_end.models.SuperAdmin;
import com.jibi.back_end.models.User;
import com.jibi.back_end.repos.AdminRepository;
import com.jibi.back_end.repos.AgentRepository;
import com.jibi.back_end.repos.ClientProfessionelRepository;
import com.jibi.back_end.repos.ClientRepository;
import com.jibi.back_end.repos.SuperAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

//     private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final ClientProfessionelRepository clientProfRepository;
    private final AgentRepository agentRepository;
    private final SuperAdminRepository superAdminRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request,String role) {
        System.out.println("Accessing login in authenticationService, login and password: "+request.getLogin()+" "+request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),request.getPassword()
                )
        );//problem is here
        String jwtToken="";
        User u=null;
            if(role.equals("Client"))
            {
                System.out.println("role is client, "+request.getLogin());
                var user = clientRepository.findByEmailOrPhoneNumber(request.getLogin(), request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                jwtToken=jwtService.generateToken(user);
                u=user;
            }
            if(role.equals("Agent"))
            {
                System.out.println("role is agent, "+request.getLogin());
                var user = agentRepository.findByEmailOrPhoneNumber(request.getLogin(), request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                jwtToken=jwtService.generateToken(user);
                u=user;
            }
            if(role.equals("ClientProf"))
            {
                System.out.println("role is clientprof, "+request.getLogin());
                var user = clientProfRepository.findByEmailOrPhoneNumber(request.getLogin(), request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                jwtToken=jwtService.generateToken(user);
                u=user;
            }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(u)
                .build();
    }

    public AdminAuthResponse loginAdmin(AuthenticationRequest request) {
        System.out.println("Accessing login in authenticationService, login and password: "+request.getLogin()+" "+request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),request.getPassword()
                )
        );
        String jwtToken="";
        boolean isSuper=true;
        // Admin a = null;
            System.out.println("role is admin, "+request.getLogin());
            SuperAdmin user = superAdminRepository.findByEmail(request.getLogin())
            .orElse(null);
            // a=user;
            if(user==null){
                Admin user2 = adminRepository.findByEmail(request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                isSuper=false;
                jwtToken=jwtService.generateToken(user2);
                // a=user2;
                return AdminAuthResponse.builder()
        .token(jwtToken).isSuperAdmin(isSuper).admin(user2)
        .build();
            }
            jwtToken=jwtService.generateToken(user);
            return AdminAuthResponse.builder()
        .token(jwtToken).isSuperAdmin(isSuper).admin(user)
        .build();
        
    }
}
