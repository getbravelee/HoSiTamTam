package com.suleekyuri.hositamtam.region.mapper;

import com.suleekyuri.hositamtam.region.dto.RegionDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegionMapper {
    List<RegionDto> findByDongCode(@Param("bcode") String bcode);
}
