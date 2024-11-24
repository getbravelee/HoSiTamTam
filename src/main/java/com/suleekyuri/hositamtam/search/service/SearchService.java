package com.suleekyuri.hositamtam.search.service;


import com.suleekyuri.hositamtam.search.dto.SuggestionDto;

import java.util.List;

public interface SearchService {
    List<SuggestionDto> getSuggestions(String keyword);
}
