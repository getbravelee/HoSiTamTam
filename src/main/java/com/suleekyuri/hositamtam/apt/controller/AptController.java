package com.suleekyuri.hositamtam.apt.controller;

import com.suleekyuri.hositamtam.apt.dto.AptDetailDto;
import com.suleekyuri.hositamtam.apt.service.AptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apt")
public class AptController {

    private final AptService aptService;

    @Autowired
    public AptController(AptService aptService) {
        this.aptService = aptService;
    }

    @GetMapping("/{aptId}")
    public AptDetailDto getAptDetail (@PathVariable String aptId) {
        return aptService.getAptDetail(aptId);
    }
}
