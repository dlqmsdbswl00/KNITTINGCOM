//package com.oxtv.controller;
//
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.oxtv.service.UserService;
//import com.oxtv.model.User;
//
//@Controller
//public class LoginController {
//
//    private final UserService userService;
//
//    public LoginController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/login")
//    public String loginForm() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam String userId, @RequestParam String userPassword,
//                        @RequestParam(required = false) String redirect,
//                        HttpSession session, Model model) {
//        User user = userService.authenticate(userId, userPassword);
//        if (user == null) {
//            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다");
//            return "login";
//        }
//
//        session.setAttribute("loginUser", user);
//
//        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
//        if (redirectUrl != null) {
//            session.removeAttribute("redirectAfterLogin");
//            return "redirect:" + redirectUrl;
//        }
//
//        return "redirect:/";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/";
//    }
//}
