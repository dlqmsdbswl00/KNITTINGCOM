package com.oxtv.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oxtv.service.UserService;
import com.oxtv.model.User;

@Controller
public class LoginController {

	private final UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String userPassword,
			@RequestParam(required = false) String redirect, HttpSession session,
			RedirectAttributes redirectAttributes) {

		System.out.println(">>> 로그인 요청 들어옴");
		System.out.println("입력 ID: " + userId);
		System.out.println("입력 PW: " + userPassword);

		User loginUser = userService.authenticate(userId, userPassword);
		if (loginUser == null) {
			System.out.println(">>> 로그인 실패 - 인증 실패");
			redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다.");
			return "redirect:/login";
		}

		System.out.println(">>> 로그인 성공 - 사용자: " + loginUser);
		session.setAttribute("loginUser", loginUser);
		System.out.println(">>> 세션에 loginUser 저장됨: " + session.getAttribute("loginUser"));

		String redirectURL = (String) session.getAttribute("redirectAfterLogin");
		session.removeAttribute("redirectAfterLogin");

		if (redirectURL != null) {
			System.out.println(">>> 리다이렉트 대상 있음: " + redirectURL);
			return "redirect:" + redirectURL;
		}

		System.out.println(">>> 기본 홈으로 리다이렉트");
		return "redirect:/"; // 기본 홈으로
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println("로그아웃 전 세션 ID: " + session.getId());
		session.invalidate();
		System.out.println("세션 무효화 완료");
		return "redirect:/";
	}
}
