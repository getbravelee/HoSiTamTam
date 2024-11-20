package com.suleekyuri.hositamtam.apt.mapper;

import com.suleekyuri.hositamtam.apt.dto.AptDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AptMapper {
    List<AptDto> getAptsByRegion(@Param("dongCode") String dongCode);
}
