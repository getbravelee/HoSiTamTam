package com.suleekyuri.hositamtam.apt.service;


import com.suleekyuri.hositamtam.apt.dto.AptDto;

import java.util.List;

public interface AptService {
    List<AptDto> getAptsByRegion(String dongCode);
}
