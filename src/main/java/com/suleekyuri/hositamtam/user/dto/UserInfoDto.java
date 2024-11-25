package com.suleekyuri.hositamtam.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private int userId;
    private String userLoginId;
    private String userNickname;
    private String userEmail;
}
