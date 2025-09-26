package com.niqdev.mono.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置類
 * 配置基本的安全設置和用戶認證
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 配置安全過濾器鏈
     * 定義哪些路徑需要認證，哪些可以匿名訪問
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // 允許所有用戶訪問登入頁面和靜態資源
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                // 其他所有請求都需要認證
                .anyRequest().authenticated()
            )
            // 配置表單登入
            .formLogin(form -> form
                .loginPage("/login")           // 自定義登入頁面
                .defaultSuccessUrl("/", true)  // 登入成功後重定向到首頁
                .failureUrl("/login?error")    // 登入失敗後重定向到登入頁面並顯示錯誤
                .permitAll()                   // 允許所有用戶訪問登入頁面
            )
            // 配置登出
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")  // 登出成功後重定向到登入頁面
                .permitAll()                        // 允許所有用戶登出
            )
            // 禁用 CSRF（在開發環境中，生產環境建議啟用）
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    /**
     * 配置用戶詳細信息服務
     * 使用數據庫存儲的用戶信息
     * 注意：Spring 會自動找到 CustomUserDetailsService 作為 UserDetailsService 的實現
     */

    /**
     * 配置密碼編碼器
     * 使用 BCrypt 進行密碼加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
