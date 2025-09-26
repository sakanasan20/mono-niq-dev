package com.niqdev.mono.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 整合測試專用配置
 * 用於 @SpringBootTest 測試，提供測試專用的 Bean
 */
@TestConfiguration
public class IntegrationTestConfig {

    /**
     * 測試專用的密碼編碼器
     */
    @Bean
    @Primary
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
