package com.suleekyuri.hositamtam.aptTrade;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class AptTradeSave {

    @XmlElement(name = "body")
    private Body body;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {
        @XmlElement(name = "items")
        private Items items;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Items {
        @XmlElement(name = "item")
        private List<Item> itemList;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        private String aptNm;
        private Integer buildYear;
        private String buyerGbn;
        private String dealAmount;
        private Integer dealYear;
        private Integer dealMonth;
        private Integer dealDay;
        private String dealingGbn;
        private Double excluUseAr;
        private Integer floor;
        private String jibun;
        private String landLeaseholdGbn;
        private String sggCd;
        private String umdNm;
        private String estateAgentSggNm;
    }
}
