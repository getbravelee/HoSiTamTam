package com.suleekyuri.hositamtam.region.service;


import com.suleekyuri.hositamtam.region.dto.RegionDto;
import java.util.List;

public interface RegionService {
    List<RegionDto> getAptListByDongCode(String bcode);
}
