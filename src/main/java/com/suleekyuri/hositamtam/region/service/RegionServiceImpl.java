package com.suleekyuri.hositamtam.region.service;

import com.suleekyuri.hositamtam.apt.dto.AptListDto;
import com.suleekyuri.hositamtam.region.dto.RegionDto;
import com.suleekyuri.hositamtam.region.mapper.RegionMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService {

    private RegionMapper regionMapper;

    @Autowired
    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public List<RegionDto> getAptsByRegion(String dongName) {
        return regionMapper.findAptListByRegion(dongName);
    }
}
