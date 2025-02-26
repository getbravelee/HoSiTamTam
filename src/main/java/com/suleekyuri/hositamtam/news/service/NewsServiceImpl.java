package com.suleekyuri.hositamtam.news.service;

import com.suleekyuri.hositamtam.news.dto.News;
import com.suleekyuri.hositamtam.news.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService{

    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    public List<News> getNews() {
        return newsMapper.getNews();
    }
}
