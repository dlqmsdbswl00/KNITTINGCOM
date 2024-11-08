package com.mini.Knit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/")
	public String redirectToHome() {
		logger.info("HOME페이지로 리다이렉트");
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home() {
		logger.info("HOME페이지 이동");
		return "home"; // home.html 템플릿을 렌더링
	}
}
