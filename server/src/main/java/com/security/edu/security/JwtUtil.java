package com.security.edu.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")           // application.yml에 정의할 예정
    private String secretKeyRaw;

    @Value("${jwt.expiration-ms}")    // application.yml에 정의할 예정
    private long expirationMs;

    private Key secretKey;

    @PostConstruct
    public void init() {
        // Base64로 인코딩되지 않은 단순 문자열을 byte[]로 바로 변환해도 됨
        this.secretKey = Keys.hmacShaKeyFor(secretKeyRaw.getBytes());
    }

    // 토큰 생성 (subject에는 username을 넣는다)
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 username(subject) 추출
    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(secretKey)
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (JwtException e) {
            return null;  // 유효하지 않은 토큰
        }
    }

    // 토큰 유효성 검사 (서명, 만료 등)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
