package com.suleekyuri.hositamtam.news.controller;

import com.suleekyuri.hositamtam.news.dto.News;
import com.suleekyuri.hositamtam.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping()
    public List<News> getNewsList() {
        return newsService.getNews();
    }
}
