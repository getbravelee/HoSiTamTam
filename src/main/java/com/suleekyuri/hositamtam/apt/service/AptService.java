package com.suleekyuri.hositamtam.apt.service;


import java.util.List;

public interface AptService {
    List<AptListDto> getAptsByRegion(String dongCode);
}
