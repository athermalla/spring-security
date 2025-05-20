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

    public static String secretKey = "c60b4c4d85db2f1bf1b80b2207ee2512322fd6175cd28e58bc85ecbe941fc816738b8fb878826c842efb88c2a404d9e20d141acb4c387cfa38ec9ead85f775509fcc2079885f0cda01c8a0d181787e69eedb68c75ea5dad8230f65c75360ff0860946c3abe294927bb94eb558ccd30eb11396a1e80b5ae640b73b12b816e56ec1c72755e75321795d2afe48848ce3f95ad2fe781a88ba181d308fd12b3928b3bf4c715b41b39d890e528a350867806f87da8978662e9ca21e0c62d73c5b50eafb2595cc3ffbb06a49f472a06700134b9beef13d1d1193dc637a47167459a60136efde379aa5dfdd53aa22ba9d367840c6793c5a4670b1b4ba7b54807ca63d94b5e385ef7fefe2602dce9fd0cf4423793673c537bd0d53d4a122a91718b186e553b979049767107722b11c9ffe5d4cf4521f6a6ff665ad04644ed7693a0f9173b1225d6e308aed013696db550f70703bd1e35a98ebdfb2a2e2833f987bd4aae54e2d3b392cc6c4b944c0ddf1efb5d822b23a9c56d1ccbd45b5410a01337ead180c070c8749edfc989b16ffd85cc63dfd904589aad729a8ad722c405279312a003b0035ac6a7ecd3a9c071b3a84de5b53338d0c8e2c7242a6c06d3c1f351067499a7c60eb69b4f1ef238b76c32a2e64965c3c634e13acf02d03e9461c2fc248d67a6c1aceed88714959a13be677d7a6646c2cfbe046f62a14e73ca4f8e30b746c6";

    public static Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        //return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
    public  String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60*5))
                .signWith(getKey(),SignatureAlgorithm.HS512)
                .compact()
                ;
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
                .getBody()
                .getSubject();
    }
    public static String getPrincipal() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
