package com.suleekyuri.hositamtam.user.service;

import com.suleekyuri.hositamtam.user.User;

public interface UserService {
    User getUserByUserLoginId(String userLoginId);
    User getUserByUsername(String username);
    void registerUser(User user);
}
