package com.niqdev.mono.service;

import com.niqdev.mono.entity.User;
import com.niqdev.mono.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定義用戶詳細信息服務
 * 從數據庫中加載用戶信息用於 Spring Security 認證
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 根據用戶名加載用戶詳細信息
     * @param username 用戶名
     * @return 用戶詳細信息
     * @throws UsernameNotFoundException 如果用戶不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用戶不存在: " + username));

        return user;
    }

    /**
     * 根據用戶 ID 查找用戶
     * @param id 用戶 ID
     * @return 用戶對象
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("用戶不存在，ID: " + id));
    }

    /**
     * 保存用戶
     * @param user 用戶對象
     * @return 保存後的用戶對象
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * 檢查用戶名是否存在
     * @param username 用戶名
     * @return 如果用戶名存在返回 true
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * 檢查郵箱是否存在
     * @param email 郵箱地址
     * @return 如果郵箱存在返回 true
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
