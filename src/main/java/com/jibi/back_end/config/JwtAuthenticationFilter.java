package com.jibi.back_end.config;


// import com.jibi.back_end.models.Role;
import com.jibi.back_end.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;
        final String role;
        System.out.println("entered the filter:");
        System.out.println("\tauthHeader: "+authHeader);
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            System.out.println("--authHeader is null:"+(authHeader==null));
            filterChain.doFilter(request,response);
            return;
        }

        jwtToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwtToken);
        role =jwtService.extractRole(jwtToken);
        System.out.println("probably extracted userEmail and role:"+userEmail+"  ****  "+role);
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsernameAndRole(userEmail,role);
            if(jwtService.isTokenValid(jwtToken,userDetails)){
                // String userRole = userDetails.getAuthorities().iterator().next().getAuthority();
                System.out.println("----The token is valid----, authorities "+userDetails.getAuthorities().toArray()[0].toString());
                // if (role.equals(userRole)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                // }
            }
        }
        filterChain.doFilter(request,response);
    }

}