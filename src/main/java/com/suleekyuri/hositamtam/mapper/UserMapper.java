package com.suleekyuri.hositamtam.mapper;

import com.suleekyuri.hositamtam.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByLoginId(String userLoginId);  // 로그인 ID로 사용자 조회
    User findByUsername(String username);    // 사용자명으로 사용자 조회
    void insertUser(User user);              // 사용자 등록
}