package com.oxtv.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oxtv.model.User;
import com.oxtv.repository.UserRepository;
import java.util.Optional;

@Configuration
public class TestDataInitializer {

	@Bean
	public CommandLineRunner initTestUser(UserRepository userRepository) {
		return args -> {
			String testUserId = "test"; // 로그인용 ID
			Optional<User> existingUser = userRepository.findByUserId(testUserId);
			if (existingUser.isEmpty()) { // null 체크로 바꾸기
				User testUser = new User();
				testUser.setUserId(testUserId);
				testUser.setUserName("테스트이름");
				testUser.setNickname("테스트별명");
				testUser.setUserPassword(testUserId); // 평문 저장 주의! (실서비스는 암호화 필수)
				testUser.setEmail("testuser@example.com");
				System.out.println("email set: " + testUser.getEmail());

				userRepository.save(testUser);
				System.out.println("✅ testuser 계정 생성 완료 (ID: test / PW: test)");
			} else {
				System.out.println("ℹ️ testuser 계정 이미 존재함");
			}
		};
	}
}
