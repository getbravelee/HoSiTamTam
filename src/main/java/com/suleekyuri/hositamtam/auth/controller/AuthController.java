package com.suleekyuri.hositamtam.auth.controller;

import com.suleekyuri.hositamtam.auth.dto.JoinDto;
import com.suleekyuri.hositamtam.auth.dto.LoginDto;
import com.suleekyuri.hositamtam.user.User;
import com.suleekyuri.hositamtam.user.service.UserService;
import com.suleekyuri.hositamtam.shared.CMRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    // 회원가입 처리
    @PostMapping("/register")
    public CMRespDto<?> registerUser(@RequestBody JoinDto joinDto) {
        try {
            // DTO를 User 엔티티로 변환
            User user = joinDto.toEntity();

            // 사용자 등록
            userService.registerUser(user);

            // 성공 응답 반환
            return new CMRespDto<>(1, "회원가입 성공", "회원가입이 완료되었습니다.");
        } catch (Exception e) {
            // 실패 응답 반환
            return new CMRespDto<>(-1, "회원가입 실패", e.getMessage());
        }
    }

    // 로그인 처리
    @PostMapping("/login")
    public CMRespDto<?> loginUser(@RequestBody LoginDto loginDto) {
        try {
            // 로그인 인증 시도
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUserLoginId(),
                            loginDto.getUserPassword()
                    );

            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 인증 성공 후 SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 성공 응답 반환
            return new CMRespDto<>(1, "로그인 성공", "로그인 성공");
        } catch (Exception e) {
            // 실패 응답 반환
            return new CMRespDto<>(-1, "로그인 실패", e.getMessage());
        }
    }
}