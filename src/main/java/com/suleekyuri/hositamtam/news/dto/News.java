package com.suleekyuri.hositamtam.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class News {
    private int id;
    private String title;
    private String url;
    private int recommendation;
    private String newsDate;
}
