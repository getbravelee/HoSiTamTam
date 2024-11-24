package com.suleekyuri.hositamtam.region.controller;

import com.suleekyuri.hositamtam.region.dto.RegionDto;
import com.suleekyuri.hositamtam.region.service.RegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
