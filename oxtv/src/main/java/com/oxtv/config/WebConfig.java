package com.oxtv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oxtv.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	LoginCheckInterceptor loginCheckInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/posts/new", // 게시글 작성 폼
				"/posts/create", // 게시글 등록 처리
				"/posts/*/edit", // 게시글 수정 폼
				"/posts/*/update", // 게시글 수정 처리
				"/posts/*/delete", // 게시글 삭제
				"/comments/**", // 댓글 CRUD
				"/my/**" // 마이페이지
		) // 보호할 URL 패턴
				.excludePathPatterns("/login", "/logout", "/register", "/css/**", "/js/**", "/img/**"); // 예외
	}
}
