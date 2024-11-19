package com.suleekyuri.hositamtam.jwt;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.suleekyuri.hositamtam.user.User;  // User DTO import

@Service
public class JwtProvider {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // JWT 생성 메서드
    public String create(User user) {
        // 기한은 생성 시점부터 1시간으로 설정
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        return Jwts.builder()
                // 서명 SECRET_KEY 설정
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                // payload에 들어갈 내용 (userLoginId를 subject로 사용)
                .setSubject(user.getUserLoginId())  // User 객체에서 userLoginId 사용
                .setIssuer("hositamtam") // JWT 발행자를 설정 (서비스명 등)
                .setIssuedAt(new Date()) // 토큰 발행 시점을 설정
                .setExpiration(expiryDate) // 토큰 만료 시간을 설정
                .compact(); // JWT를 최종 문자열로 생성
    }

    // JWT 검증 및 UserId 반환 메서드
    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // userLoginId를 반환
    }
}
