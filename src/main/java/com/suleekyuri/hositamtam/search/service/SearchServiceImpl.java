package com.suleekyuri.hositamtam.search.service;

import com.suleekyuri.hositamtam.mapper.SearchMapper;
import com.suleekyuri.hositamtam.search.dto.SuggestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchMapper searchMapper;

    @Autowired
    public SearchServiceImpl(SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    @Override
    public List<SuggestionDto> getSuggestions(String keyword) {
        return searchMapper.findByLocationContaining(keyword);
    }
}
