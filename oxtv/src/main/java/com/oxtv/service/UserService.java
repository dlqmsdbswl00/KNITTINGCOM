package com.oxtv.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oxtv.model.User;
import com.oxtv.repository.UserRepository;

import jakarta.annotation.PostConstruct;

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
		System.out.println(">>> [AUTH] 유저 ID로 조회 시도: " + userId);
		Optional<User> userOpt = userRepository.findByUserId(userId);
		if (userOpt.isEmpty()) {
			System.out.println(">>> [AUTH] 사용자 없음");
			return null;
		}

		User user = userOpt.get();
		System.out.println(">>> [AUTH] 사용자 찾음: " + user);

		boolean matched = passwordEncoder.matches(rawPassword, user.getUserPassword());
		System.out.println(">>> [AUTH] 비밀번호 일치 여부: " + matched);

		if (!matched) {
			return null;
		}

		return user;
	}

	public void updateUserInfo(User updatedUser) {
		User user = userRepository.findById(updatedUser.getId()).orElseThrow(() -> new RuntimeException("사용자 없음"));

		user.setEmail(updatedUser.getEmail());
		user.setNickname(updatedUser.getNickname());
		user.setUserPassword(passwordEncoder.encode(updatedUser.getUserPassword()));

		userRepository.save(user);
	}

	public User findById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

}
