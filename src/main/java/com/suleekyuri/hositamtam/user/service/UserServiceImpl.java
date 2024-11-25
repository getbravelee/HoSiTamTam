package com.suleekyuri.hositamtam.user.service;

import com.suleekyuri.hositamtam.auth.dto.LoginDto;
import com.suleekyuri.hositamtam.jwt.JwtProvider;
import com.suleekyuri.hositamtam.user.dto.UserInfoDto;
import com.suleekyuri.hositamtam.user.mapper.UserMapper;
import com.suleekyuri.hositamtam.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;  // 비밀번호 암호화 처리

    @Autowired
    public UserServiceImpl(UserMapper userMapper, JwtProvider jwtProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;  // BCryptPasswordEncoder 주입
    }

    @Override
    public User getUserByUserLoginId(String userLoginId) {
        return userMapper.findByLoginId(userLoginId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public void registerUser(User user) {
        // 아이디가 4글자 미만인 경우 예외 발생
        if (user.getUserLoginId().length() < 4) {
            throw new IllegalArgumentException("아이디는 4글자 이상이어야 합니다.");
        }

        // 이미 해당 아이디가 존재하는 경우
        if (getUserByUserLoginId(user.getUserLoginId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 비밀번호를 암호화하여 새로운 User 객체를 생성
        String encryptedPassword = passwordEncoder.encode(user.getUserLoginPassword());  // 암호화
        // 불변 객체이므로, 암호화된 비밀번호로 새 User 객체 생성
        User newUser = User.builder()
                .userId(user.getUserId())
                .userLoginId(user.getUserLoginId())
                .userLoginPassword(encryptedPassword)
                .userNickname(user.getUserNickname())
                .userEmail(user.getUserEmail())
                .build();
        userMapper.insertUser(newUser); // 암호화된 비밀번호로 새 User 객체를 저장
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        User user = getUserByUserLoginId(loginDto.getUserLoginId());
        if (user == null || !passwordEncoder.matches(loginDto.getUserPassword(), user.getUserLoginPassword())) {
            // 비밀번호가 맞지 않으면 예외 던지기
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // 인증 진행
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUserLoginId(), loginDto.getUserPassword());

        // 인증을 AuthenticationManager에 위임
        authenticationManager.authenticate(authenticationToken);

        // JWT 토큰 생성
        return jwtProvider.create(user);
    }


    // 회원정보 조회
    @Override
    public UserInfoDto getUserInfo(Long userId) {
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
        return new UserInfoDto(user.getUserId(), user.getUserLoginId(), user.getUserNickname(), user.getUserEmail());
    }

    @Override
    public void updateUserInfo(Long userId, UserInfoDto userInfoDto) {
        // 사용자 조회
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        // nickname 외의 정보 수정 여부 확인
        if (userInfoDto.getUserEmail() != null && !userInfoDto.getUserEmail().equals(user.getUserEmail())) {
            throw new IllegalArgumentException("닉네임 외의 정보는 수정할 수 없습니다.");
        }

        // 새로운 User 객체를 생성하여 수정된 nickname만 업데이트
        User updatedUser = User.builder()
                .userId(user.getUserId())  // 기존 userId 유지
                .userLoginId(user.getUserLoginId())  // 기존 userLoginId 유지
                .userLoginPassword(user.getUserLoginPassword())  // 기존 userLoginPassword 유지
                .userNickname(userInfoDto.getUserNickname())  // 수정된 닉네임
                .userEmail(user.getUserEmail())  // 기존 email 유지
                .build();

        // 수정된 User 객체를 데이터베이스에 반영
        userMapper.updateUserInfo(updatedUser);
    }

    // 회원 탈퇴
    @Override
    public void deleteUser(Long userId) {
        // 사용자 조회
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        // 회원 탈퇴 처리
        userMapper.updateUserStatus(userId);
    }

    @Override
    public boolean isUserLoginIdAvailable(String userLoginId) {
        return !userMapper.existsByUserLoginId(userLoginId);    }
}
