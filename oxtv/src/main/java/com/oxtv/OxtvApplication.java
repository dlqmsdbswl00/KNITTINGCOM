package com.oxtv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OxtvApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(OxtvApplication.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			System.out.println(passwordEncoder.encode("admin"));
		};
	}
}
