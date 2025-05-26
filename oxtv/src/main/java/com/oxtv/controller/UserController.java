package com.oxtv.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oxtv.model.User;
import com.oxtv.repository.UserRepository;

import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
public class UserController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/signup")
	public String showSignupForm(User user) {
		return "signup"; // signup.html 또는 signup.jsp
	}

	@PostMapping("/signup")
	public String processSignup(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}

		// 아이디 중복 체크
		if (userRepository.existsByUserId(user.getUserId())) {
			model.addAttribute("userIdError", "이미 사용중인 아이디입니다.");
			return "signup";
		}

		// 이메일 중복 체크
		if (userRepository.existsByEmail(user.getEmail())) {
			model.addAttribute("emailError", "이미 사용중인 이메일입니다.");
			return "signup";
		}

		if (userRepository.existsByNickname(user.getNickname())) {
			model.addAttribute("nicknameError", "이미 사용중인 닉네임입니다.");
			return "signup";
		}

		// 비밀번호 암호화
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

		userRepository.save(user);

		return "redirect:/login"; // 회원가입 끝나면 로그인 페이지로
	}
}
