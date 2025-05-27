package com.oxtv.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);

		// 로그인 안 돼있으면 로그인 페이지로 + 현재 URL 저장
		if (session == null || session.getAttribute("loginUser") == null) {
			String uri = request.getRequestURI();
			String query = request.getQueryString();
			String target = (query == null) ? uri : uri + "?" + query;

			session = request.getSession(); // 없으면 새로 만들어
			session.setAttribute("redirectAfterLogin", target);

			// AJAX 요청 판단 (헤더에 X-Requested-With가 "XMLHttpRequest"인 경우)
			String ajaxHeader = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(ajaxHeader)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401
			} else {
				response.sendRedirect("/login");
			}
			return false;
		}
		return true; // 로그인 됐으면 통과
	}
}