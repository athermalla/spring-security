package com.fresco.ecommerce.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    public static String secretKey = "5b3634e4934fca15f205d642f854784c25345d1e287212cf4382a15e3e1c2cb9";

    public static Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
    public  String createToken(String username) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *5))
                .setSubject(username)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public  boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parse(token);

        return true;
    }
    public  String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();

    }
    public static String getPrincipal() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
