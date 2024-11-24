package com.suleekyuri.hositamtam.favorite.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class FavoriteDto {

    private Long userId;       // 사용자의 ID
    private String aptId;        // 아파트 ID
    private Boolean isFavorite; // 즐겨찾기 여부
}
