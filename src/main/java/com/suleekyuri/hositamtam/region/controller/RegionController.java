package com.suleekyuri.hositamtam.region.controller;

import com.suleekyuri.hositamtam.apt.service.AptService;
import com.suleekyuri.hositamtam.region.dto.RegionDto;
import com.suleekyuri.hositamtam.region.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/{dongName}")
    public List<RegionDto> getAptsByRegion(@PathVariable String dongName) {
        return regionService.getAptsByRegion(dongName);
    }
}
