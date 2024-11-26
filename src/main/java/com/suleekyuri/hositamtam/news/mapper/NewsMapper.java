package com.suleekyuri.hositamtam.news.mapper;

import com.suleekyuri.hositamtam.news.dto.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {
    List<News> getNews();
}
