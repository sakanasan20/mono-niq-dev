package com.niqdev.mono.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.niqdev.mono.service.PasswordResetService;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    // 顯示「忘記密碼」頁
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    // 提交「忘記密碼」表單 -> 產生 token（實務上應寄送 email，這裡先直接顯示）
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("usernameOrEmail") String usernameOrEmail, Model model) {
        try {
            String token = passwordResetService.createResetToken(usernameOrEmail);
            model.addAttribute("resetLink", "/reset-password?token=" + token);
            model.addAttribute("message", "已產生重設連結（示範用）：" + "/reset-password?token=" + token);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "forgot-password";
    }

    // 顯示「重設密碼」頁
    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam("token") String token, Model model) {
        try {
            passwordResetService.validateToken(token);
            model.addAttribute("token", token);
            return "reset-password";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forgot-password";
        }
    }

    // 提交新密碼
    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam("token") String token,
                                      @RequestParam("password") String password,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      Model model) {
        try {
            if (!password.equals(confirmPassword)) {
                throw new IllegalArgumentException("兩次輸入的密碼不一致");
            }
            passwordResetService.resetPassword(token, password);
            model.addAttribute("message", "密碼已更新，請重新登入");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("token", token);
            return "reset-password";
        }
    }
}


