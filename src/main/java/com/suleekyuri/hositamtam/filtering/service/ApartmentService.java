package com.suleekyuri.hositamtam.filtering.service;

import com.suleekyuri.hositamtam.filtering.dto.ApartmentDto;
import com.suleekyuri.hositamtam.filtering.mapper.ApartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {

    @Autowired
    private final ApartmentMapper apartmentMapper;

    public ApartmentService(ApartmentMapper apartmentMapper) {
        this.apartmentMapper = apartmentMapper;
    }

    public List<ApartmentDto> getFilteredApartments(Integer area, Integer price, String type) {
        return apartmentMapper.findByFilters(area, price, type);
    }
}

