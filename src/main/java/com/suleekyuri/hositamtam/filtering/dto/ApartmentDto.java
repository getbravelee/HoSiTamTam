package com.suleekyuri.hositamtam.filtering.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentDto {
    private Long id;
    private Integer area;       // 평수
    private Integer price;      // 가격 (만원 단위)
    private String type;        // 매물 종류
    private String locationName; // 위치
}
