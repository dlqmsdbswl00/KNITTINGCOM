package com.oxtv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.oxtv.security.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/", "/signup", "/login", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated() //나머지 페이지는 로그인 필요
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login") // POST /login으로 인증 처리
                .defaultSuccessUrl("/", false) // 로그인 성공 후 기본 이동 경로 (여기서 true면 무조건 이곳으로)
                .failureUrl("/login?error") // 로그인 실패 시 이동 경로                
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
