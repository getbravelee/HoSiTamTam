package com.suleekyuri.hositamtam.region.controller;

import com.suleekyuri.hositamtam.apt.dto.AptDto;
import com.suleekyuri.hositamtam.apt.service.AptService;
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

    private final AptService aptService;

    @Autowired
    public RegionController(AptService aptService) {
        this.aptService = aptService;
    }

    @GetMapping("/{dongCode}")
    public List<AptDto> getAptsByRegion(@PathVariable String dongCode) {
        return aptService.getAptsByRegion(dongCode);
    }
}
