package com.suleekyuri.hositamtam.exception;

import com.suleekyuri.hositamtam.shared.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // IllegalArgumentException을 처리하는 메서드
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CMRespDto<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // 예외 메시지를 포함한 응답 반환
        CMRespDto<?> response = new CMRespDto<>(-1, "회원정보 수정 실패", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}