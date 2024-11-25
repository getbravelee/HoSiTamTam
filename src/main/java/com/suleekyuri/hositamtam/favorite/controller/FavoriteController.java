package com.suleekyuri.hositamtam.favorite.controller;

import com.suleekyuri.hositamtam.exception.UnauthorizedException;
import com.suleekyuri.hositamtam.favorite.dto.FavoriteDto;
import com.suleekyuri.hositamtam.favorite.service.FavoriteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // 사용자의 즐겨찾기 목록 조회
    @GetMapping("/{userId}")
    public List<FavoriteDto> getFavorites(@PathVariable Long userId) {
        return favoriteService.getFavorites(userId);
    }

    // 즐겨찾기 추가
    @PostMapping("/add/{aptId}")
    public void addFavorite(@PathVariable String aptId) {
        Long userId = getAuthenticatedUserId(); // 로그인된 사용자 ID 가져오기
        favoriteService.addFavorite(userId, aptId); // 즐겨찾기 추가
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/remove/{aptId}")
    public void removeFavorite(@PathVariable String aptId) {
        Long userId = getAuthenticatedUserId();
        favoriteService.removeFavorite(userId, aptId); // 즐겨찾기 삭제
    }

    // 아파트가 즐겨찾기 상태인지 확인
    @GetMapping("/isFavorite/{aptId}")
    public Boolean isFavorite(@PathVariable String aptId) {
        Long userId = getAuthenticatedUserId();
        return favoriteService.isFavorite(userId, aptId);
    }

    // 로그인된 사용자 ID를 가져오는 메서드 (인증되지 않은 사용자 처리)
    private Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("principal: " + principal);  // principal 값 확인
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
