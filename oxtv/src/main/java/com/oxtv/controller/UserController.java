package com.oxtv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oxtv.model.User;
import com.oxtv.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showSignupForm(User user) {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }

        // 서비스에 중복 체크 및 회원가입 로직 위임
        String errorMessage = userService.register(user);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "signup";
        }

        return "redirect:/login";
    }
}
