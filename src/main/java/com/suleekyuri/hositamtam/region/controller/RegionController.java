package com.suleekyuri.hositamtam.region.controller;

import com.suleekyuri.hositamtam.region.dto.RegionDto;
import com.suleekyuri.hositamtam.region.service.RegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/region")
public class RegionController {

    private RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/{bcode}")
    public List<RegionDto> getAptList(@PathVariable String bcode) {
        return regionService.getAptListByDongCode(bcode);
    }

    @GetMapping
    public List<RegionDto> getFilteredApartments(
            @RequestParam(value = "areaMin", required = false) Integer areaMin,
            @RequestParam(value = "areaMax", required = false) Integer areaMax,
            @RequestParam(value = "priceMin", required = false) Integer priceMin,
            @RequestParam(value = "priceMax", required = false) Integer priceMax,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "bcode") String bcode) {

        return regionService.getFilteredApartments(areaMin, areaMax, priceMin, priceMax, type, bcode);
    }
}
