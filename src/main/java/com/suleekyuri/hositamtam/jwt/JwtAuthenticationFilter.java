package com.suleekyuri.hositamtam.jwt;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            if (token != null && !token.equalsIgnoreCase("null") && !request.getRequestURI().equals("/auth/login")) {
                // 토큰을 통해 사용자 인증
                String userId = jwtProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID: " + userId);

                // Authentication 객체 생성
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // AuthenticationPrincipal (사용자 ID)
                        null,
                        AuthorityUtils.NO_AUTHORITIES); // 권한은 없다고 설정 (필요시 추가 가능)

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 인증 정보 설정
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            log.error("Could not set user authentication in security context", e);
        }
        filterChain.doFilter(request, response); // 필터 체인으로 요청 전달
    }

    // HTTP 요청에서 Bearer 토큰 추출
    private String parseBearerToken(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(TOKEN_PREFIX)) {
            return accessToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
