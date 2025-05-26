package com.oxtv.security;

import com.oxtv.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();  // 로그인 아이디로 사용하는 필드
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료 기능 없으면 true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금 기능 없으면 true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 기능 없으면 true
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 기능 없으면 true
    }

    // 필요하면 User 객체 자체를 꺼낼 getter 추가
    public User getUser() {
        return user;
    }
}
