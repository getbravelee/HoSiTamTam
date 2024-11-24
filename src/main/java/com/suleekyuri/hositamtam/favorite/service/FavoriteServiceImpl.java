package com.suleekyuri.hositamtam.favorite.service;

import com.suleekyuri.hositamtam.favorite.Favorite;
import com.suleekyuri.hositamtam.favorite.dto.FavoriteDto;
import com.suleekyuri.hositamtam.favorite.mapper.FavoriteMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteServiceImpl(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    // 사용자의 즐겨찾기 목록 조회
    public List<FavoriteDto> getFavorites(Long userId) {
        return favoriteMapper.getFavoritesByUserId(userId);
    }

    // 즐겨찾기 추가
    public void addFavorite(Long userId, String aptId) {
        // 즐겨찾기 추가 (INSERT)
        favoriteMapper.addFavorite(userId, aptId);
    }

    // 즐겨찾기 삭제
    public void removeFavorite(Long userId, String aptId) {
        // 즐겨찾기 삭제 (DELETE)
        favoriteMapper.removeFavorite(userId, aptId);
    }

    // 아파트가 즐겨찾기 상태인지 확인
    public Boolean isFavorite(Long userId, String aptId) {
        return favoriteMapper.isFavorite(userId, aptId);
    }
}
