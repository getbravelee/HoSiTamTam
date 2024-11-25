package com.suleekyuri.hositamtam.user.service;

import com.suleekyuri.hositamtam.auth.dto.LoginDto;
import com.suleekyuri.hositamtam.user.User;
import com.suleekyuri.hositamtam.user.dto.UserInfoDto;

public interface UserService {
    User getUserByUserLoginId(String userLoginId);
    User getUserByUsername(String username);
    void registerUser(User user);
    String loginUser(LoginDto loginDto);
    boolean isUserLoginIdAvailable(String userLoginId);
    // 회원정보 조회
    UserInfoDto getUserInfo(Long userId);

    // 회원정보 수정
    void updateUserInfo(Long userId, UserInfoDto userInfoDto);

    // 회원 탈퇴
    void deleteUser(Long userId);

}
