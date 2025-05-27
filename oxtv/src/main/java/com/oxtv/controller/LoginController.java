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
	public String login(@RequestParam String username, @RequestParam String password,
			@RequestParam(required = false) String redirect, HttpSession session,
			RedirectAttributes redirectAttributes) {

		User loginUser = userService.authenticate(username, password);
		if (loginUser == null) {
			redirectAttributes.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다");
			return "redirect:/login";
		}

		session.setAttribute("loginUser", loginUser);

		// 1순위: 쿼리 파라미터
	    if (redirect != null && !redirect.isBlank()) {
	        return "redirect:" + redirect;
	    }

	    // 2순위: 세션에 저장된 URL
	    String redirectURL = (String) session.getAttribute("redirectAfterLogin");
	    session.removeAttribute("redirectAfterLogin");

	    if (redirectURL != null) {
	        return "redirect:" + redirectURL;
	    }

	    return "redirect:/"; // 기본 홈으로
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
