package com.suleekyuri.hositamtam.aptTrade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "apt_trade")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AptTradeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aptName;
    private Integer buildYear;
    private String buyerType;
    private String dealAmount;
    private Integer dealYear;
    private Integer dealMonth;
    private Integer dealDay;
    private String dealingType;
    private Double excluUseArea;
    private Integer floor;
    private String jibun;
    private String landLeaseholdGbn;
    private String sggCode;
    private String umdName;
    private String estateAgentAddress;
}
