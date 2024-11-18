package com.suleekyuri.hositamtam.filtering.controller;


import com.suleekyuri.hositamtam.filtering.dto.ApartmentDto;
import com.suleekyuri.hositamtam.filtering.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    @Autowired
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @GetMapping
    public List<ApartmentDto> getFilteredApartments(
            @RequestParam(value = "area", required = false) Integer area,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "type", required = false) String type) {
        return apartmentService.getFilteredApartments(area, price, type);
    }
}

