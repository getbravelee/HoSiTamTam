package com.suleekyuri.hositamtam.region.dto;
import lombok.Getter;

@Getter
public class AptListDto {
    private int aptId;
    private String aptName;
    private String aptTradeType;
    private int aptMaxSalesPrice;
    private String aptImg;
    private boolean isLike;
}
