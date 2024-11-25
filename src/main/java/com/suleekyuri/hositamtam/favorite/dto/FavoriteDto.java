package com.suleekyuri.hositamtam.favorite.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class FavoriteDto {

    private Long userId;       // 사용자의 ID
    private String aptId;        // 아파트 ID
    private Boolean isFavorite; // 즐겨찾기 여부

    // 아파트 정보 추가
    private String aptName;    // 아파트 이름
    private String local1;     // 지역1
    private String local2;     // 지역2
    private String local3;     // 지역3
    private int maxSalesPrice; // 최고 가격
    private int totalHome; // 총세대수
    private double parkingPerHome; // 가구당 주차대수
    private String image;
}
