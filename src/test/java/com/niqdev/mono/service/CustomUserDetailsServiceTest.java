package com.niqdev.mono.service;

import com.niqdev.mono.entity.User;
import com.niqdev.mono.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.niqdev.mono.config.IntegrationTestConfig;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CustomUserDetailsService 的測試類
 * 測試數據庫用戶認證功能
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Import(IntegrationTestConfig.class)
class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        // 創建測試用戶
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword(passwordEncoder.encode("testpass"));
        testUser.setRole("USER");
        testUser.setEmail("test@example.com");
        testUser.setFullName("測試用戶");
        testUser.setEnabled(true);
        testUser.setAccountNonExpired(true);
        testUser.setAccountNonLocked(true);
        testUser.setCredentialsNonExpired(true);
        
        userRepository.save(testUser);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // 測試成功加載用戶
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");
        
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // 測試用戶不存在的情況
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });
    }

    @Test
    void testFindById_Success() {
        // 測試根據 ID 查找用戶
        User foundUser = userDetailsService.findById(testUser.getId());
        
        assertNotNull(foundUser);
        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void testFindById_UserNotFound() {
        // 測試查找不存在的用戶 ID
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.findById(999L);
        });
    }

    @Test
    void testExistsByUsername() {
        // 測試檢查用戶名是否存在
        assertTrue(userDetailsService.existsByUsername("testuser"));
        assertFalse(userDetailsService.existsByUsername("nonexistent"));
    }

    @Test
    void testExistsByEmail() {
        // 測試檢查郵箱是否存在
        assertTrue(userDetailsService.existsByEmail("test@example.com"));
        assertFalse(userDetailsService.existsByEmail("nonexistent@example.com"));
    }

    @Test
    void testSaveUser() {
        // 測試保存用戶
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword(passwordEncoder.encode("newpass"));
        newUser.setRole("USER");
        newUser.setEmail("new@example.com");
        newUser.setFullName("新用戶");
        newUser.setEnabled(true);
        
        User savedUser = userDetailsService.save(newUser);
        
        assertNotNull(savedUser.getId());
        assertEquals("newuser", savedUser.getUsername());
        assertTrue(userRepository.existsByUsername("newuser"));
    }
}
