package com.suleekyuri.hositamtam.user.service;

import com.suleekyuri.hositamtam.mapper.UserMapper;
import com.suleekyuri.hositamtam.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByUserLoginId(String userLoginId) {
        return null;
    }

    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public void registerUser(User user) {
        userMapper.insertUser(user);
    }
}
