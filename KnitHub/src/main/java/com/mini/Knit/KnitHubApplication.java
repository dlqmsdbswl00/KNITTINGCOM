package com.mini.Knit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mini.Knit.mappers")	// 매퍼 위치 설정

public class KnitHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnitHubApplication.class, args);
	}

}
