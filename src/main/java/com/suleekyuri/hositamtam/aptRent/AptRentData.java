package com.suleekyuri.hositamtam.aptRent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "apt_rent")
@Data
@NoArgsConstructor
public class AptRentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID

    private String aptName;       // 아파트 이름
    private int buildYear;        // 건축 연도
    private String contractTerm;  // 계약 기간
    private String contractType;  // 계약 유형
    private int dealDay;          // 거래 일
    private int dealMonth;        // 거래 월
    private int dealYear;         // 거래 연도
    private String deposit;         // 보증금
    private double excluUseArea;  // 전용 면적
    private int floor;            // 층수
    private String jibun;         // 지번
    private long monthlyRent;     // 월세
    private String preDeposit;      // 이전 보증금
    private long preMonthlyRent;  // 이전 월세
    private String sggCode;       // 시군구 코드
    private String umdName;       // 읍면동명
    private String useRRRight;    // 사용 권리
}