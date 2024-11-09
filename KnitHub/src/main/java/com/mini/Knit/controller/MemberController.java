package com.mini.Knit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mini.Knit.command.AddUserCommand;
import com.mini.Knit.command.LoginCommand;
import com.mini.Knit.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping(value = "/addUser")
	public String addUserForm(Model model) {
		System.out.println("회원가입폼 이동");

		// 회원가입폼에서 addUserCommand객체를 사용하는 코드가 작성되어 있어서
		// null일경우 오류가 발생하기때문에 보내줘야 함
		model.addAttribute("addUserCommand", new AddUserCommand());

		return "member/addUserForm";
	}

	@PostMapping(value = "/addUser")
	public String addUser(@Validated @ModelAttribute AddUserCommand addUserCommand, BindingResult result, Model model) {
		System.out.println("회원가입하기");

		// 비밀번호가 일치하는지 체크
		if (!addUserCommand.isPasswordMatch()) {
			result.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호가 일치하지 않습니다.");
		}

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			return "member/addUserForm";
		}

		try {
			memberService.addUser(addUserCommand);
			System.out.println("회원가입 성공");
			return "redirect:/user/login"; // 회원가입 후 로그인 페이지로 리다이렉트
		} catch (Exception e) {
			System.out.println("회원가입실패");
			e.printStackTrace();
			model.addAttribute("errorMessage", "회원가입 처리 중 오류가 발생했습니다.");
			return "member/addUserForm";
		}

	}

	@ResponseBody
	@GetMapping(value = "/idChk")
	public Map<String, String> idChk(String id) {
		System.out.println("ID중복체크");

		String resultId = memberService.idChk(id);
		// json객체로 보내기 위해 Map에 담아서 응답
		// text라면 그냥 String으로 보내도 됨
		Map<String, String> map = new HashMap<>();
		map.put("id", resultId);
		return map;
	}

	// 로그인 폼 이동
	@GetMapping(value = "/login")
	public String loginForm(Model model) {
		model.addAttribute("loginCommand", new LoginCommand());
		return "member/login";
	}

	// 로그인 실행
	@PostMapping(value = "/login")
	public String login(@Validated LoginCommand loginCommand, BindingResult result, Model model,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			System.out.println("로그인 유효값 오류");
			return "member/login";
		}

		// 로그인 결과를 세션에 설정
		String path = memberService.login(loginCommand, request, model);

		if (path.equals("redirect:/home")) { // 로그인 성공 시
			request.getSession().setAttribute("user", loginCommand); // 세션에 사용자 정보 저장
	        model.addAttribute("user", loginCommand); // 모델에 사용자 정보 추가
	        return "redirect:/"; // 홈 화면으로 리다이렉트
	    }

	    return "member/login"; // 로그인 실패 시 로그인 페이지로 다시 이동
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		System.out.println("로그아웃");
		request.getSession().invalidate();
		return "redirect:/";
	}

	// 나의 정보 조회

	// 나의 정보 수정

}
