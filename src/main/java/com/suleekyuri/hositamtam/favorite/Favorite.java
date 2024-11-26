package com.suleekyuri.hositamtam.favorite;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Favorite {

    private final Long userId;        // 사용자의 ID
    private final String aptId;         // 아파트 ID
    private final Boolean isFavorite; // 즐겨찾기 여부
}
