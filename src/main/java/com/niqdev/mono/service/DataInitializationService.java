package com.niqdev.mono.service;

import com.niqdev.mono.entity.User;
import com.niqdev.mono.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 數據初始化服務
 * 在應用程式啟動時初始化用戶數據
 */
@Service
public class DataInitializationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 在應用程式完全啟動後初始化數據
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeData() {
        try {
            // 檢查是否已經有用戶數據
            if (userRepository.count() == 0) {
                initializeUsers();
                System.out.println("用戶數據初始化完成！");
            } else {
                System.out.println("用戶數據已存在，跳過初始化。");
            }
        } catch (Exception e) {
            System.err.println("數據初始化失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 初始化用戶數據
     */
    private void initializeUsers() {
        // 創建管理員用戶
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        admin.setEmail("admin@niqdev.com");
        admin.setFullName("系統管理員");
        admin.setEnabled(true);
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        userRepository.save(admin);

        // 創建一般用戶
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole("USER");
        user.setEmail("user@niqdev.com");
        user.setFullName("一般用戶");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userRepository.save(user);

        // 創建測試用戶
        User test = new User();
        test.setUsername("test");
        test.setPassword(passwordEncoder.encode("test123"));
        test.setRole("USER");
        test.setEmail("test@niqdev.com");
        test.setFullName("測試用戶");
        test.setEnabled(true);
        test.setAccountNonExpired(true);
        test.setAccountNonLocked(true);
        test.setCredentialsNonExpired(true);
        userRepository.save(test);

        System.out.println("用戶數據初始化完成！");
        System.out.println("管理員帳戶: admin / admin123");
        System.out.println("一般用戶: user / user123");
        System.out.println("測試用戶: test / test123");
    }
}
