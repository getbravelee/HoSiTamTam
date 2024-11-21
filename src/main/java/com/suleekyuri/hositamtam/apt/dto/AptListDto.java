package com.suleekyuri.hositamtam.apt.dto;
import lombok.Getter;

@Getter
public class AptListDto {
    private int id;
    private String aptName;
    private String aptTradeType;
    private int aptMaxSalesPrice;
    private String aptImg;
    private boolean isLike;
}
