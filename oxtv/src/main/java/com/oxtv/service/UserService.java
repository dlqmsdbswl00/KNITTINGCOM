package com.oxtv.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.oxtv.model.User;
import com.oxtv.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 처리 (중복 체크 + 암호화 + 저장)
    // 에러 메시지 리턴, 없으면 null 리턴
    public String register(User user) {
        if (userRepository.existsByUserId(user.getUserId())) {
            return "이미 사용중인 아이디입니다.";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return "이미 사용중인 이메일입니다.";
        }
        if (userRepository.existsByNickname(user.getNickname())) {
            return "이미 사용중인 닉네임입니다.";
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
        return null;
    }

    // 로그인 인증 처리
    // 인증 성공 시 User 리턴, 실패 시 null 리턴
    public User authenticate(String userId, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUserId(userId);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();  // Optional에서 User 꺼내기
        if (!passwordEncoder.matches(rawPassword, user.getUserPassword())) {
            return null;
        }
        return user;
    }

}
