package com.suleekyuri.hositamtam.auth.dto;

import com.suleekyuri.hositamtam.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(title = "JoinDto(회원가입정보)", description = "회원가입 시 필요한 아이디, 비밀번호, 닉네임, 이메일의 정보를 담습니다")
@Getter
@Setter
public class JoinDto {
    @Schema(description = "회원 로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "ssafy")
    private String userLoginId;
    @Schema(description = "회원 로그인 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userPassword;
    @Schema(description = "회원 닉네임", requiredMode = Schema.RequiredMode.REQUIRED, example = "닉네임")
    private String userNickname;
    @Schema(description = "회원 이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "ssafy@ssafy.com")
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
