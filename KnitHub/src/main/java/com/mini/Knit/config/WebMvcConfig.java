package com.mini.Knit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private LoginInterceptor loginInterceptor;

	// 구현된 interceptor 객체를 등록한다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor()).order(1) // 우선순위 설정
				.addPathPatterns("/**") // 전체 요청에 대해 적용
				.excludePathPatterns(
						"/error", 
						"/board/boardList", 
						"/board/boardDetail", 
						"/board/mulDel",
						"/board/boardUpdate", 
						"/", 
						"/user/login",    // 로그인 페이지 예외
	                    "/user/addUser",  // 회원가입 페이지 예외
						"/css/**", 
						"/js/**");

//		registry.addInterceptor(new LoginInterceptor())
//		.order(2)
//		.addPathPatterns("/**")   //전체 요청에 대해 적용
//		.excludePathPatterns("/board","/","/user/**","/css/**","/js/**");
	}
}
