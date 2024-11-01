package com.mini.Knit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Logger 인스턴스 정의
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    
    @GetMapping(value = "/")
    public String showHomePage() {
        logger.info("HOME 페이지 이동");
        return "home"; // templates 폴더에 있는 home.html을 호출
    }
}
