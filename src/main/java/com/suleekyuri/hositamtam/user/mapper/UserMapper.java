package com.suleekyuri.hositamtam.user.mapper;

import com.suleekyuri.hositamtam.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // 로그인 ID로 사용자 조회
    User findByLoginId(String userLoginId);

    // 사용자명으로 사용자 조회
    User findByUsername(String username);

    // 사용자 ID로 사용자 조회
    User findByUserId(Long userId);

    // 사용자 등록
    void insertUser(User user);

    // 아이디 중복 체크
    boolean existsByUserLoginId(@Param("userLoginId") String userLoginId);

    // 사용자 정보 수정
    void updateUserInfo(User user);

    // 회원 탈퇴 (사용자의 is_deleted를 true로, deleted_at을 현재 시간으로 설정)
    void updateUserStatus(Long userId);
}