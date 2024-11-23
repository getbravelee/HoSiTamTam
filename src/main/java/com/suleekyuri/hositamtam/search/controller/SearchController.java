package com.suleekyuri.hositamtam.search.controller;


import com.suleekyuri.hositamtam.search.dto.SuggestionDto;
import com.suleekyuri.hositamtam.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/suggestions")
    public List<SuggestionDto> getSuggestions(@RequestParam("keyword") String keyword) {
        try {
            List<SuggestionDto> suggestionDto = searchService.getSuggestions(keyword);
            for (SuggestionDto s : suggestionDto) {
                System.out.println(s.getDongName());
            }
            return suggestionDto;
        } catch (Exception e) {
            System.err.println("Error fetching suggestions: " + e.getMessage());
            return new ArrayList<>(); // 빈 리스트 반환
        }
    }
}
