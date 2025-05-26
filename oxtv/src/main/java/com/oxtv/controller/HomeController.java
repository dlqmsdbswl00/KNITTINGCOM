package com.oxtv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // src/main/webapp/WEB-INF/views/index.jsp 반환
    }
}
