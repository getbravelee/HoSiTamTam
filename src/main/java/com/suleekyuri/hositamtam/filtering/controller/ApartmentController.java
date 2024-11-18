package com.hositamtam.filtering.controller;

import com.hositamtam.filtering.dto.ApartmentDto;
import com.hositamtam.filtering.service.ApartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public List<ApartmentDto> getFilteredApartments(
            @RequestParam(value = "area", required = false) Integer area,
            @RequestParam(value = "price", required = false) Integer price,
            @RequestParam(value = "type", required = false) String type) {
        return apartmentService.getFilteredApartments(area, price, type);
    }
}

