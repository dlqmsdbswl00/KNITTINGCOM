package com.mini.Knit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//	authorizeRequests() : 시큐리티 처리에 HttpServletRequest를 사용한다는 것을 의미
//	requestMatchers("주소") : 지정한 주소에 대해 예외를 두어 설정
//	permitAll() : 앞에 지정한 주소를 모두에게 접근 허가
//	anyRequest().authenticated() : 다른 어떤 접근에도 보안 검사를 한다.
//	formLogin().loginPage("주소") : 로그인 페이지로 주소값으로 이동 설정

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 기능 비활성화
		http.cors(AbstractHttpConfigurer::disable) 
		    .csrf(AbstractHttpConfigurer::disable) // cors,csrf 비활성화
		    .formLogin(AbstractHttpConfigurer::disable);//loginform 비활성화
		
		return http.build();
	}
	
	//패스워드 암호화 객체
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
