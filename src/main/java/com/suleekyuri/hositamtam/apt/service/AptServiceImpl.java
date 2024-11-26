package com.suleekyuri.hositamtam.apt.service;

import com.suleekyuri.hositamtam.apt.dto.AptDetailDto;
import com.suleekyuri.hositamtam.apt.dto.AptListDto;
import com.suleekyuri.hositamtam.apt.mapper.AptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AptServiceImpl implements AptService {

    private final AptMapper aptMapper;

    @Autowired
    public AptServiceImpl(AptMapper aptMapper) {
        this.aptMapper = aptMapper;
    }

    @Override
    public AptDetailDto getAptDetail(String aptId) {
        return aptMapper.findAptDetail(aptId);
    }
}
