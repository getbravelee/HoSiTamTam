package com.suleekyuri.hositamtam.filtering.service;

import com.suleekyuri.hositamtam.filtering.dto.FilteringDto;
import com.suleekyuri.hositamtam.filtering.mapper.FilteringMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilteringService {

    @Autowired
    private final FilteringMapper apartmentMapper;

    public FilteringService(FilteringMapper apartmentMapper) {
        this.apartmentMapper = apartmentMapper;
    }

    public List<FilteringDto> getFilteredApartments(Integer area, Integer price, String type) {
        return apartmentMapper.findByFilters(area, price, type);
    }
}

