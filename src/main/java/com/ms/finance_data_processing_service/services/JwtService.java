package com.ms.finance_data_processing_service.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiry-time-in-minute}")
    private int expiryTimeInMinute;

    public String generateToken(String username,HashMap<String, Object> claims){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000L *60*expiryTimeInMinute))
                .addClaims(claims)
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignedKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Claims verifySignatureAdnExtractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token){
        return verifySignatureAdnExtractAllClaims(token).getSubject();
    }

    public Date getExpiration(String token){
        return verifySignatureAdnExtractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
