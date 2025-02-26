package com.suleekyuri.hositamtam.apt;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Apt {
    private final String id;                        // 아파트 ID
    private final String name;                      // 아파트 이름
    private final String local1;                    // 지역1
    private final String local2;                    // 지역2
    private final String local3;                    // 지역3
    private final String local2Code;                // 지역2 코드
    private final String local3Code;                // 지역3 코드
    private final String bunji;                     // 번지
    private final String oldAddress;                // 구주소
    private final String roadAddress;               // 도로명주소
    private final String bcode;                     // B코드
    private final String lat;                       // 위도
    private final String lng;                       // 경도
    private final String usageApprovalDate;         // 사용 승인일
    private final String soldUnits;                 // 분양 세대 수
    private final String builder;                   // 시공사
    private final String totalUnits;                // 총 세대 수
    private final String totalBuildings;            // 총 동 수
    private final String maxFloors;                 // 최고층수
    private final String minFloors;                 // 최저층수
    private final String parkingPerUnit;            // 가구당 주차 대수
    private final String totalParking;              // 총 주차 대수
    private final String parkingLocation;           // 주차 위치
    private final String heatingMethod;             // 난방 방식
    private final String heatingFuel;               // 난방 연료
    private final String amenitiesJson;             // 편의시설 (JSON 형식)
    private final String floorAreaRatio;            // 용적율
    private final String buildingCoverageRatio;     // 건폐율
    private final String constructionCompany;       // 건설사
    private final String managementOfficePhone;     // 관리사무소 전화번호
    private final String description;               // 아파트 설명
    private final String seismicDesign;             // 내진설계 여부 (Y or N)
    private final String imageUrl;                  // 아파트 이미지 URL
    private final String relateDanji;               // 관련 단지 (JSON 형식)
    private final String averageRentPrice;          // 평균 임대료
    private final String averageSalesPrice;         // 평균 매매가
    private final String minSalesPrice;             // 최소 매매가
    private final String maxSalesPrice;             // 최대 매매가
    private final String roomTypesJson;             // 방 타입 (JSON 형식)
    private final String elementarySchoolZoneName;  // 초등학교 통학구역명
    private final String elementarySchoolZoneCode;  // 초등학교 통학구역 코드
    private final String middleSchoolZoneName;      // 중학교 통학구역명
    private final String middleSchoolZoneCode;      // 중학교 통학구역 코드
    private final String highSchoolZoneName;        // 고등학교 통학구역명
    private final String highSchoolZoneCode;        // 고등학교 통학구역 코드
}
