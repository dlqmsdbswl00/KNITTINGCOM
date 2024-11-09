package com.mini.Knit.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	// controller로 진입하기 전에 실행하는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		System.out.println("현재 요청 경로: " + request.getRequestURI());

		// 로그아웃 경로는 예외 처리 (로그인 여부와 관계없이 접근 허용)
		if (request.getRequestURI().equals("/user/logout")) {
			return true;
		}

		// 로그인하지 않은 상태에서 로그인, 회원가입 경로를 접근하려 할 때
		if (session.getAttribute("user") == null) { // 로그인X
			// 로그인 필요시 로그인 페이지로 리다이렉트
			if (request.getRequestURI().equals("/user/addUser") || request.getRequestURI().equals("/user/idChk")) {
				return true; // 회원가입 및 아이디 체크 경로는 예외 처리
			}
			System.out.println("로그인 필요");
			response.sendRedirect("/user/login"); // 로그인 페이지로
			return false;
		} else {
			return true;
		}
	}

}
