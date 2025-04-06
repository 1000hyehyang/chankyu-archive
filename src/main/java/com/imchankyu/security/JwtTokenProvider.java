package com.imchankyu.security;

import com.imchankyu.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.Principal;
import java.util.Date;

/**
 * JwtTokenProvider - JWT 생성 및 검증, 정보 추출 역할 수행
 */
@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final UserRepository userRepository;

    // Access Token 유효 기간: 1시간
    private static final long EXPIRATION_MS = 1000 * 60 * 60;

    // Refresh Token 유효 기간: 7일
    private static final long REFRESH_EXPIRATION_MS = 1000L * 60 * 60 * 24 * 7;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            UserRepository userRepository
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.userRepository = userRepository;
    }

    /**
     * Access Token 생성 (이메일만 포함)
     */
    public String createToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성 (역할 없음)
     */
    public String createRefreshToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * JWT에서 사용자 이메일 추출
     */
    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Principal 기반으로 사용자 ID 반환
     */
    public Long getUserIdFromPrincipal(Principal principal) {
        String email = principal.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 이메일의 사용자를 찾을 수 없습니다."))
                .getId();
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
