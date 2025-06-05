package com.oxtv.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
	    this.userService = userService;
	    this.passwordEncoder = passwordEncoder;
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
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("passwordConfirm") String passwordConfirm,
            HttpSession session,
            RedirectAttributes redirectAttrs) {
		User loginUser = (User) session.getAttribute("loginUser");

		// 로그인 여부 + 본인 확인
		if (loginUser == null || !loginUser.getId().equals(updatedUser.getId())) {
			return "redirect:/login";
		}

		// 기존 비밀번호 확인
		// ✔ 암호화된 비밀번호와 평문 비교
		if (!passwordEncoder.matches(currentPassword, loginUser.getUserPassword())) {
		    redirectAttrs.addFlashAttribute("error", "기존 비밀번호가 일치하지 않습니다.");
		    return "redirect:/mypage";
		}

	    // 새 비밀번호가 입력된 경우에만 확인
	    if (updatedUser.getUserPassword() != null && !updatedUser.getUserPassword().isEmpty()) {
	        if (!updatedUser.getUserPassword().equals(passwordConfirm)) {
	            redirectAttrs.addFlashAttribute("error", "새 비밀번호 확인이 일치하지 않습니다.");
	            return "redirect:/mypage";
	        }
	    } else {
	        // 새 비밀번호가 비어있으면 기존 비밀번호 유지하도록 세팅
	        updatedUser.setUserPassword(loginUser.getUserPassword());
	    }

		// 업데이트 처리
		userService.updateUserInfo(updatedUser);
		session.setAttribute("loginUser", updatedUser); 
		
		// 세션 갱신
	    session.setAttribute("loginUser", userService.findById(loginUser.getId()));

	    redirectAttrs.addFlashAttribute("successMessage", "정보가 성공적으로 수정되었습니다.");
		return "redirect:/mypage";
	}
}
