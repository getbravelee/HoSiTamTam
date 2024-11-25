package com.suleekyuri.hositamtam.favorite.mapper;

import com.suleekyuri.hositamtam.favorite.dto.FavoriteDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FavoriteMapper {

    // 사용자의 즐겨찾기 목록 조회
    List<FavoriteDto> getFavoritesByUserId(@Param("userId") Long userId);

    // 즐겨찾기 상태 토글 (추가/삭제)
    void addFavorite(@Param("userId") Long userId, @Param("aptId") String aptId);

    // 아파트가 즐겨찾기 상태인지 확인
    Boolean isFavorite(@Param("userId") Long userId, @Param("aptId") String aptId);

    // 즐겨찾기 삭제
    void removeFavorite(@Param("userId") Long userId, @Param("aptId") String aptId);
}