package com.user.service;

import com.user.model.UserLogIn;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil  implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserLogIn userLogInDTO){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userLogInDTO.getEmailAddress());
    }

    private String doGenerateToken(Map<String, Object> claims, String emailAddress) {
        return Jwts.builder().setClaims(claims).setSubject(emailAddress).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String auth) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(auth).getBody();
    }
}
