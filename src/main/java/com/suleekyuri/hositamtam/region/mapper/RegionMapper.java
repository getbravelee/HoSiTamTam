package com.suleekyuri.hositamtam.region.mapper;

import com.suleekyuri.hositamtam.region.dto.RegionDto;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionMapper {
    List<RegionDto> findAptListByRegion(@Param("dongName") String dongName);
}
