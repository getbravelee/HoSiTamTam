package com.suleekyuri.hositamtam.aptRent;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class AptRentSave {

    @XmlElement(name = "body")
    private Body body;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {
        @XmlElement(name = "items")
        private AptRentSave.Items items;

        @XmlElement(name = "totalCount", nillable = true)
        private Integer totalCount;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Items {
        @XmlElement(name = "item")
        private List<AptRentSave.Item> itemList;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        private String aptNm; // 아파트 이름
        private int buildYear; // 건축 연도
        private String contractTerm; // 계약 기간
        private String contractType; // 계약 유형
        private int dealDay; // 거래 일
        private int dealMonth; // 거래 월
        private int dealYear; // 거래 연도
        private String deposit; // 보증금 (문자열로 받아서 필요 시 변환)
        private double excluUseAr; // 전용 면적
        private int floor; // 층수
        private String jibun; // 지번
        private long monthlyRent; // 월세 (문자열로 받아서 필요 시 변환)
        private String preDeposit; // 이전 보증금
        private long preMonthlyRent; // 이전 월세
        private String sggCd; // 시군구 코드
        private String umdNm; // 읍면동명
        private String useRRRight; // 사용 권리
    }
}