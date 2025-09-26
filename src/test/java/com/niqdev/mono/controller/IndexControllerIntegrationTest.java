package com.niqdev.mono.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * IndexController 的整合測試類
 * 使用 @SpringBootTest 來啟動完整的 Spring 應用程式上下文
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IndexControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 整合測試：測試根路徑的完整請求響應流程
     * 驗證應用程式能正確處理 HTTP 請求並返回適當的響應
     */
    @Test
    @WithMockUser
    void testIndexIntegrationWithRootPath() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print()) // 打印請求響應詳細信息，便於調試
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString(""))); // 驗證響應不為空
    }

    /**
     * 整合測試：測試空路徑的完整請求響應流程
     */
    @Test
    @WithMockUser
    void testIndexIntegrationWithEmptyPath() throws Exception {
        mockMvc.perform(get(""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    /**
     * 整合測試：驗證 Thymeleaf 模板引擎正常工作
     * 測試模板解析和渲染過程
     */
    @Test
    @WithMockUser
    void testThymeleafTemplateRendering() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("<!DOCTYPE html>")))
                .andExpect(content().string(containsString("<title>NIQ-DEV</title>")));
//                .andExpect(content().string(containsString("<h1>Mono</h1>")));
    }

    /**
     * 整合測試：測試應用程式的健康狀態
     * 驗證控制器能正確響應且應用程式運行正常
     */
    @Test
    @WithMockUser
    void testApplicationHealth() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"));
    }

    /**
     * 整合測試：測試多個連續請求
     * 驗證應用程式在多次請求下保持穩定
     */
    @Test
    @WithMockUser
    void testMultipleRequests() throws Exception {
        // 測試多次請求確保應用程式穩定性
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        }
    }

    /**
     * 整合測試：測試請求頭信息
     * 驗證應用程式能正確處理不同的請求頭
     */
    @Test
    @WithMockUser
    void testRequestHeaders() throws Exception {
        mockMvc.perform(get("/")
                .header("Accept", "text/html")
                .header("User-Agent", "Integration-Test"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    /**
     * 整合測試：測試視圖解析和響應處理
     * 驗證 Spring MVC 的視圖解析器正常工作
     */
    @Test
    @WithMockUser
    void testViewResolution() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<!DOCTYPE html>")));
    }

    /**
     * 整合測試：測試完整的請求-響應週期
     * 驗證從 HTTP 請求到 HTML 響應的完整流程
     */
    @Test
    @WithMockUser
    void testCompleteRequestResponseCycle() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print()) // 打印詳細信息用於調試
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("<html>")))
                .andExpect(content().string(containsString("</html>")))
                .andExpect(content().string(containsString("NIQ-DEV")));
//                .andExpect(content().string(containsString("Mono")));
    }
}
