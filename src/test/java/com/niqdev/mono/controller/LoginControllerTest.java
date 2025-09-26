package com.niqdev.mono.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import com.niqdev.mono.config.TestSecurityConfig;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * LoginController 的 JUnit 測試類
 * 測試登入相關的功能
 */
@WebMvcTest(LoginController.class)
@Import(TestSecurityConfig.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 測試登入頁面可以匿名訪問
     */
    @Test
    @WithAnonymousUser
    void testLoginPageAccessible() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    /**
     * 測試登入頁面包含必要的表單元素
     */
    @Test
    @WithAnonymousUser
    void testLoginPageContent() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("用戶名")))
                .andExpect(content().string(containsString("密碼")))
                .andExpect(content().string(containsString("登入")))
                .andExpect(content().string(containsString("測試帳戶")));
    }
}
