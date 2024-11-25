package com.suleekyuri.hositamtam.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// JPA를 사용하지 않기 때문에 기본생성자를 만들지 않아도 된다. 빌더 패턴으로 불변 객체 만드는 것이 더 나은 것 같다.
@Getter
public class User implements UserDetails {
    private final int userId;
    private final String userLoginId;
    private final String userLoginPassword;
    private final String userNickname;
    private final String userEmail;

    @Builder // 빌더 패턴을 사용해 final 필드 초기화
    public User(int userId, String userLoginId, String userLoginPassword, String userNickname, String userEmail) {
        this.userId = userId;
        this.userLoginId = userLoginId;
        this.userLoginPassword = userLoginPassword;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 반환하는 로직을 구현합니다. 예시로 빈 리스트 반환
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.userLoginPassword;
    }

    @Override
    public String getUsername() {
        return this.userLoginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았다고 가정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았다고 가정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호가 만료되지 않았다고 가정
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되어 있다고 가정
    }
}