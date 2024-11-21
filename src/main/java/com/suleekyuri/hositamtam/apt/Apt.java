package com.suleekyuri.hositamtam.apt;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Builder
public class Apt {
    private final String id;
    private final String name;
    private final String local1;
    private final String local2;
    private final String local3;
    private final String oldAddress;
    private final String roadAddress;
    private final BigDecimal lat;
    private final BigDecimal lng;
    private final String usageApprovalDate;
    private final int soldUnits;
    private final String builder;
    private final int totalUnits;
    private final int totalBuildings;
    private final int maxFloors;
    private final int minFloors;
    private final BigDecimal parkingPerUnit;
    private final int totalParking;
    private final String parkingLocation;
    private final String heatingMethod;
    private final String heatingFuel;
    private final String amenitiesJson; // JSON as String
    private final BigDecimal floorAreaRatio;
    private final BigDecimal buildingCoverageRatio;
    private final String constructionCompany;
    private final String managementOfficePhone;
    private final String description;
    private final String seismicDesign;
    private final String imageUrl;
    private final String siteTourJson; // JSON as String
    private final String siteTourDate;
    private final String priceChartJson; // JSON as String
    private final String relatedApartmentsJson; // JSON as String
    private final String roomTypesJson; // JSON as String
    private final String elementarySchoolZoneName;
    private final String elementarySchoolZoneCode;
    private final String middleSchoolZoneName;
    private final String middleSchoolZoneCode;
    private final String highSchoolZoneName;
    private final String highSchoolZoneCode;
}
