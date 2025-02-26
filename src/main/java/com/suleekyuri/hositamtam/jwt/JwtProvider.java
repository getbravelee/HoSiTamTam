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

        // 로그로 토큰 생성 정보 확인
        System.out.println("User Login ID: " + user.getUserLoginId());
        System.out.println("Token Expiry Date: " + expiryDate);

        String token = Jwts.builder()
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .setSubject(String.valueOf(user.getUserId()))  // User 객체에서 userLoginId 사용
                .setIssuer("hositamtam")  // JWT 발행자를 설정 (서비스명 등)
                .setIssuedAt(new Date())  // 토큰 발행 시점을 설정
                .setExpiration(expiryDate)  // 토큰 만료 시간을 설정
                .compact();  // JWT를 최종 문자열로 생성

        // 생성된 토큰 출력
        System.out.println("Generated JWT Token: " + token);

        return token;
    }

    // JWT 검증 및 UserId 반환 메서드
    public String validateAndGetUserId(String token) {
        System.out.println("Validating JWT Token: " + token);

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject();  // userLoginId를 반환

        // 유효한 JWT에서 추출된 userId 확인
        System.out.println("Extracted User ID from Token: " + userId);

        return userId;
    }
}
