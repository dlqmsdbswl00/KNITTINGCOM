package com.oxtv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oxtv.model.User;
import com.oxtv.service.UserService;

@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/signup")
	public String showSignupForm(User user) {
		return "signup";
	}

	@PostMapping("/signup")
	public String processSignup(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}

		// 서비스에 중복 체크 및 회원가입 로직 위임
		String errorMessage = userService.register(user);
		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
			return "signup";
		}

		return "redirect:/login";
	}

	@GetMapping("/mypage")
	public String myPage(Model model, HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login"; // 비로그인 상태일 경우
		}

		model.addAttribute("user", loginUser);
		return "mypage"; // 마이페이지 JSP
	}

	@PostMapping("/mypage/update")
	public String updateUserInfo(@ModelAttribute("user") User updatedUser,
			@RequestParam("passwordConfirm") String passwordConfirm, HttpSession session,
			RedirectAttributes redirectAttrs) {
		User loginUser = (User) session.getAttribute("loginUser");

		// 로그인 여부 + 본인 확인
		if (loginUser == null || !loginUser.getId().equals(updatedUser.getId())) {
			return "redirect:/login";
		}

		// 비밀번호 확인
		if (!updatedUser.getUserPassword().equals(passwordConfirm)) {
			redirectAttrs.addFlashAttribute("error", "비밀번호 확인이 일치하지 않습니다.");
			return "redirect:/mypage";
		}

		// 업데이트 처리
		userService.updateUserInfo(updatedUser);
		session.setAttribute("loginUser", updatedUser); // 세션 갱신

		redirectAttrs.addFlashAttribute("success", "회원 정보가 수정되었습니다.");
		return "redirect:/mypage";
	}
}
