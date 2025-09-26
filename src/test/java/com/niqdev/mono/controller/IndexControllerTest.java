package com.niqdev.mono.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.niqdev.mono.config.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * IndexController 的 JUnit 測試類
 * 使用 @WebMvcTest 註解來測試 Web 層
 */
@WebMvcTest(IndexController.class)
@Import(TestSecurityConfig.class)
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 測試根路徑 "/" 的 GET 請求
     * 驗證返回狀態碼為 200 且視圖名稱為 "index"
     */
    @Test
    @WithMockUser
    void testIndexWithRootPath() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /**
     * 測試空路徑 "" 的 GET 請求
     * 驗證返回狀態碼為 200 且視圖名稱為 "index"
     */
    @Test
    @WithMockUser
    void testIndexWithEmptyPath() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /**
     * 測試根路徑 "/" 的 GET 請求
     * 驗證返回的內容類型
     */
    @Test
    @WithMockUser
    void testIndexContentType() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
}
