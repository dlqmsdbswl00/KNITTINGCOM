package com.oxtv.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.oxtv.repository.UserRepository;
import com.oxtv.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String loginForm() {
		return "login"; // login.jsp 보여줌
	}

	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String userPassword, HttpSession session,
			Model model) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			model.addAttribute("errorMessage", "아이디가 존재하지 않습니다");
			return "login"; // login.jsp 다시 보여줌
		}

		if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
			model.addAttribute("errorMessage", "비밀번호가 틀렸습니다");
			return "login";
		}

		session.setAttribute("loginUser", user); // 로그인 세션 생성
		return "redirect:/"; // 로그인 성공하면 메인 페이지로
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 제거
		return "redirect:/"; // 메인 페이지로 이동
	}

}
