package com.suleekyuri.hositamtam.mapper;


import com.suleekyuri.hositamtam.filtering.dto.ApartmentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApartmentMapper {
    List<ApartmentDto> findByFilters(@Param("area") Integer area,
                                     @Param("price") Integer price,
                                     @Param("type") String type);
}

