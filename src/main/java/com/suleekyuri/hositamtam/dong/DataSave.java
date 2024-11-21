package com.suleekyuri.hositamtam.dong;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "results")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSave {

    @XmlElementWrapper(name = "data")
    @XmlElement(name = "item")
    private List<Item> items;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        @XmlElement(name = "col")
        private List<Column> columns;

        @Getter
        @Setter
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Column {
            @XmlAttribute(name = "name")
            private String name;

            @XmlValue
            private String value;
        }
    }
}
