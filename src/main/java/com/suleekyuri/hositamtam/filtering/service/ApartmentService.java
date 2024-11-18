package com.hositamtam.filtering.service;

import com.hositamtam.filtering.dto.ApartmentDto;
import com.hositamtam.filtering.mapper.ApartmentMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentMapper apartmentMapper;

    public List<ApartmentDto> getFilteredApartments(Integer area, Integer price, String type) {
        return apartmentMapper.findByFilters(area, price, type);
    }
}

