package com.suleekyuri.hositamtam.auth.dto;

import com.suleekyuri.hositamtam.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    private String userLoginId;
    private String userPassword;
    private String userNickname;
    private String userEmail;

    public User toEntity() {
        return User.builder()
                .userLoginId(this.userLoginId)
                .userLoginPassword(this.userPassword)
                .userNickname(this.userNickname)
                .userEmail(this.userEmail)
                .build();
    }

    public LoginDto toLoginDto() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUserLoginId(this.userLoginId);
        loginDto.setUserPassword(this.userPassword);
        return loginDto;
    }
}
