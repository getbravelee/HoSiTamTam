package com.suleekyuri.hositamtam.user.service;

import com.suleekyuri.hositamtam.mapper.UserMapper;
import com.suleekyuri.hositamtam.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByUserLoginId(String userLoginId) {
        // userLoginId를 기준으로 사용자를 조회
        return userMapper.findByLoginId(userLoginId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void registerUser(User user) {
        userMapper.insertUser(user);
    }
}