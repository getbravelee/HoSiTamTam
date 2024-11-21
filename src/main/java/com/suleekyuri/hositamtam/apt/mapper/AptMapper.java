package com.suleekyuri.hositamtam.apt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AptMapper {
    List<AptListDto> getAptsByRegion(@Param("dongCode") String dongCode);
}
