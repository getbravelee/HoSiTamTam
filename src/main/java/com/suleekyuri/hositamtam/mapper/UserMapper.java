package com.suleekyuri.hositamtam.mapper;

import com.suleekyuri.hositamtam.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByLoginId(String username);
    User findByUsername(String username);
    void insertUser(User user);
}
