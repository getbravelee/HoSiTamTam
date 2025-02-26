package com.suleekyuri.hositamtam.search.mapper;

import com.suleekyuri.hositamtam.search.dto.SuggestionDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchMapper {
    List<SuggestionDto> findByLocationContaining(@Param("keyword") String keyword);
}
