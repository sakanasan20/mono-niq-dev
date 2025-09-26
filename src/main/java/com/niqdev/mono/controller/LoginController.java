package com.niqdev.mono.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登入控制器
 * 處理登入相關的頁面請求
 */
@Controller
public class LoginController {

    /**
     * 顯示登入頁面
     * @return 登入頁面視圖名稱
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
