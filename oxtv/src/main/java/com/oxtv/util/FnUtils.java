package com.oxtv.util;

import com.oxtv.model.Role;
import com.oxtv.model.User;

import jakarta.servlet.http.HttpSession;

public class FnUtils {
    public static boolean isAdmin(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        return loginUser != null && loginUser.getRole() == Role.ADMIN;
    }
}
