package com.mini.Knit.controller;

import java.util.HashMap;
import java.util.Map;

import com.mini.Knit.dtos.UserBoardDTO;
import com.mini.Knit.service.UserBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserBoardController {

    private final UserBoardService userBoardService;
    private static final Logger logger = LoggerFactory.getLogger(UserBoardController.class);

    @Autowired
    public UserBoardController(UserBoardService userBoardService) {
        this.userBoardService = userBoardService;
    }

    @GetMapping("/addUserForm")
    public String showRegisterForm(Model model) {
        model.addAttribute("addUserCommand", new UserBoardDTO());
        return "member/addUserForm"; // 회원가입 페이지로 이동
    }

    @PostMapping("/addUserForm")
    public String registerUser(UserBoardDTO userBoardDTO, Model model) {
    	try {
            boolean result = userBoardService.registerUser(userBoardDTO);
            if (result) {
                model.addAttribute("message", "회원가입이 완료되었습니다.");
                return "member/login"; // 로그인 페이지로 이동
            } else {
                model.addAttribute("error", "회원가입에 실패했습니다.");
                return "member/addUserForm"; // 회원가입 페이지로 돌아가기
            }
        } catch (Exception e) {
            logger.error("회원가입 중 오류 발생", e);
            model.addAttribute("error", "오류 발생: " + e.getMessage());
            return "member/addUserForm"; // 오류 발생 시 회원가입 페이지로 돌아가기
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "member/login"; // 로그인 페이지로 이동
    }

    @PostMapping("/login")
    public String login(UserBoardDTO userBoardDTO, Model model) {
        UserBoardDTO loggedUser = userBoardService.login(userBoardDTO.getUsername());
        if (loggedUser != null) {
            // 로그인 성공
            return "home"; // 홈 페이지로 이동
        } else {
            model.addAttribute("error", "로그인에 실패했습니다.");
            return "member/login"; // 로그인 페이지로 돌아가기
        }
    }
}
