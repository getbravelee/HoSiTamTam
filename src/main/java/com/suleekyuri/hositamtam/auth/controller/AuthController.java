package com.suleekyuri.hositamtam.auth.controller;

import com.suleekyuri.hositamtam.auth.dto.JoinDto;
import com.suleekyuri.hositamtam.auth.dto.LoginDto;
import com.suleekyuri.hositamtam.exception.UnauthorizedException;
import com.suleekyuri.hositamtam.jwt.JwtProvider;
import com.suleekyuri.hositamtam.user.User;
import com.suleekyuri.hositamtam.user.dto.UserInfoDto;
import com.suleekyuri.hositamtam.user.service.UserService;
import com.suleekyuri.hositamtam.shared.CMRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
@Tag(name = "AUTH 컨트롤러", description = "회원가입, 로그인")
public class AuthController {

    private final UserService userService;


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 처리
    @Operation(summary = "회원가입", description = "회원가입 진행합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CMRespDto.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<CMRespDto<?>> registerUser(@RequestBody JoinDto joinDto) {
        try {
            // DTO를 User 엔티티로 변환
            User user = joinDto.toEntity();
            log.info("회원가입 아이디: {}", user.getUserLoginId());

            // 사용자 등록
            userService.registerUser(user);

            // 성공 응답 반환 (HTTP 200)
            CMRespDto<?> response = new CMRespDto<>(1, "회원가입 성공", "회원가입이 완료되었습니다.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 실패 응답 반환 (HTTP 400 - Bad Request)
            CMRespDto<?> response = new CMRespDto<>(-1, "회원가입 실패", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("회원가입 실패", e);
            // 실패 응답 반환 (HTTP 500)
            CMRespDto<?> response = new CMRespDto<>(-1, "회원가입 실패", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 로그인 처리
    @Operation(summary = "로그인", description = "로그인 진행합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 실패")
    })
    @PostMapping("/login")
    public CMRespDto<?> login(@RequestBody LoginDto loginDto) {
        try {
            // 로그인 비즈니스 로직 서비스에 위임
            String token = userService.loginUser(loginDto);

            // 성공 응답 반환 (200 OK, 토큰 포함)
            return new CMRespDto<>(1, "로그인 성공", token);
        } catch (Exception e) {
            log.error("로그인 실패", e);
            // 실패 응답 반환 (HTTP 401)
            return new CMRespDto<>(-1, "로그인 실패", e.getMessage());
        }
    }

    // 로그아웃 처리
    @Operation(summary = "로그아웃", description = "로그아웃 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패")
    })
    @PostMapping("/logout")
    public CMRespDto<?> logout() {
        try {
            // 현재 인증된 사용자인지 확인
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
                throw new UnauthorizedException("로그인되지 않은 사용자입니다.");
            }

            // 로그아웃은 클라이언트에서 토큰을 삭제하는 방식
            // 서버에서는 토큰을 관리하지 않기 때문에, 별도 처리 없이 응답만 보냅니다.

            return new CMRespDto<>(1, "로그아웃 성공", "로그아웃이 완료되었습니다.");
        } catch (UnauthorizedException e) {
            log.error("로그인되지 않은 사용자가 로그아웃을 시도했습니다.");
            return new CMRespDto<>(-1, "로그아웃 실패", "로그인된 사용자만 로그아웃할 수 있습니다.");
        } catch (Exception e) {
            log.error("로그아웃 실패", e);
            return new CMRespDto<>(-1, "로그아웃 실패", e.getMessage());
        }
    }

    @GetMapping("/checkId")
    public ResponseEntity<Map<String, Object>> checkUsername(@RequestParam String userLoginId) {
        boolean isAvailable = userService.isUserLoginIdAvailable(userLoginId);

        Map<String, Object> response = new HashMap<>();
        response.put("userLoginId", userLoginId);
        response.put("available", isAvailable);

        return ResponseEntity.ok(response);
    }

    // 로그인된 사용자의 회원정보 조회
    @GetMapping("/detail")
    public CMRespDto<UserInfoDto> getUserInfo() {
        Long userId = getAuthenticatedUserId(); // 로그인된 사용자 ID 가져오기
        UserInfoDto userInfo = userService.getUserInfo(userId);
        return new CMRespDto<>(1, "회원정보 조회 성공", userInfo);
    }

    // 로그인된 사용자의 회원정보 수정 (nickname만 수정)
    @PostMapping("/detail/update")
    public CMRespDto<?> updateUserInfo(@RequestBody UserInfoDto userInfoDto) {
        Long userId = getAuthenticatedUserId(); // 로그인된 사용자 ID 가져오기
        userService.updateUserInfo(userId, userInfoDto); // 회원정보 수정 (nickname만 수정)
        return new CMRespDto<>(1, "회원정보 수정 성공", "회원정보가 성공적으로 수정되었습니다.");
    }

    // 로그인된 사용자의 회원 탈퇴
    @PostMapping("/delete")
    public CMRespDto<?> deleteUser() {
        Long userId = getAuthenticatedUserId(); // 로그인된 사용자 ID 가져오기
        userService.deleteUser(userId); // 회원 탈퇴 처리
        return new CMRespDto<>(1, "회원탈퇴 성공", "회원탈퇴가 완료되었습니다.");
    }

    // 로그인된 사용자 ID를 가져오는 메서드 (인증되지 않은 사용자 처리)
    private Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null || !(principal instanceof String)) {
            throw new UnauthorizedException("로그인된 사용자가 아닙니다.");
        }

        // principal을 String에서 Long으로 변환
        try {
            return Long.parseLong((String) principal);
        } catch (NumberFormatException e) {
            throw new UnauthorizedException("유효하지 않은 사용자 ID입니다.");
        }
    }
}