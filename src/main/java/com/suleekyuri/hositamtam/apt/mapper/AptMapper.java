package com.suleekyuri.hositamtam.apt.mapper;

import com.suleekyuri.hositamtam.apt.dto.AptDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AptMapper {
    AptDetailDto findAptDetail(@Param("aptId") String aptId);
}
