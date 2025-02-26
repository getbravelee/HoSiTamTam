package com.suleekyuri.hositamtam.region.mapper;

import com.suleekyuri.hositamtam.region.dto.RegionDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RegionMapper {
    List<RegionDto> findByDongCode(@Param("bcode") String bcode);

    List<RegionDto> findByDongCode(@Param("areaMin") Integer areaMin,
                                   @Param("areaMax") Integer areaMax,
                                   @Param("priceMin") Integer priceMin,
                                   @Param("priceMax") Integer priceMax,
                                   @Param("type") String type,
                                   @Param("bcode") String bcode);

}
