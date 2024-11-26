package com.suleekyuri.hositamtam.apt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AptDetailDto {
    private String id;                        // 아파트 ID
    private String name;                      // 아파트 이름
    private String local1;                    // 지역1
    private String local2;                    // 지역2
    private String local3;                    // 지역3
    private String local2Code;                // 지역2 코드
    private String local3Code;                // 지역3 코드
    private String bunji;                     // 번지
    private String oldAddress;                // 구주소
    private String roadAddress;               // 도로명주소
    private String bcode;                     // B코드
    private String lat;                       // 위도
    private String lng;                       // 경도
    private String usageApprovalDate;         // 사용 승인일
    private String soldUnits;                 // 분양 세대 수
    private String builder;                   // 시공사
    private String totalUnits;                // 총 세대 수
    private String totalBuildings;            // 총 동 수
    private String maxFloors;                 // 최고층수
    private String minFloors;                 // 최저층수
    private String parkingPerUnit;            // 가구당 주차 대수
    private String totalParking;              // 총 주차 대수
    private String parkingLocation;           // 주차 위치
    private String heatingMethod;             // 난방 방식
    private String heatingFuel;               // 난방 연료
    private String amenitiesJson;             // 편의시설 (JSON 형식)
    private String floorAreaRatio;            // 용적율
    private String buildingCoverageRatio;     // 건폐율
    private String constructionCompany;       // 건설사
    private String managementOfficePhone;     // 관리사무소 전화번호
    private String description;               // 아파트 설명
    private String seismicDesign;             // 내진설계 여부 (Y or N)
    private String imageUrl;                  // 아파트 이미지 URL
    private String relateDanji;               // 관련 단지 (JSON 형식)
    private String averageRentPrice;          // 평균 임대료
    private String averageSalesPrice;         // 평균 매매가
    private String minSalesPrice;             // 최소 매매가
    private String maxSalesPrice;             // 최대 매매가
    private String roomTypesJson;             // 방 타입 (JSON 형식)
    private String elementarySchoolZoneName;  // 초등학교 통학구역명
    private String elementarySchoolZoneCode;  // 초등학교 통학구역 코드
    private String middleSchoolZoneName;      // 중학교 통학구역명
    private String middleSchoolZoneCode;      // 중학교 통학구역 코드
    private String highSchoolZoneName;        // 고등학교 통학구역명
    private String highSchoolZoneCode;        // 고등학교 통학구역 코드
}
