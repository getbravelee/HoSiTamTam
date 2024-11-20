package com.suleekyuri.hositamtam.filtering.mapper;


import com.suleekyuri.hositamtam.filtering.dto.FilteringDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilteringMapper {
    List<FilteringDto> findByFilters(@Param("area") Integer area,
                                     @Param("price") Integer price,
                                     @Param("type") String type);
}

