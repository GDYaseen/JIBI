package com.jibi.back_end.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("deprecation")
@Service
public class JwtService {


    private static final String SECRET_KEY = "49t3PapD9Fhk4CEeK4Tdx9TKrHBTBzNs0hKh+AW1IMCqicVFW37fa9V1Do9fIBy8";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public String generateToken(UserDetails userDetails) {
        System.out.println("generating Token, " + userDetails.getUsername() + "-" + userDetails.getPassword() + "-" + userDetails.getAuthorities());
        Map<String, Object> claims = new HashMap<>();
        claims.put("authority", userDetails.getAuthorities().iterator().next().getAuthority());
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails) {
        String token =Jwts.builder().setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
        System.out.println("the generated token is "+token);
        return token;
    }
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("authority", String.class));
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }




    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
