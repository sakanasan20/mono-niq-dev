package com.niqdev.mono.repository;

import com.niqdev.mono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用戶數據庫訪問接口
 * 提供基本的 CRUD 操作和自定義查詢方法
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根據用戶名查找用戶
     * @param username 用戶名
     * @return 用戶對象（如果存在）
     */
    Optional<User> findByUsername(String username);

    /**
     * 檢查用戶名是否存在
     * @param username 用戶名
     * @return 如果用戶名存在返回 true
     */
    boolean existsByUsername(String username);

    /**
     * 根據郵箱查找用戶
     * @param email 郵箱地址
     * @return 用戶對象（如果存在）
     */
    Optional<User> findByEmail(String email);

    /**
     * 檢查郵箱是否存在
     * @param email 郵箱地址
     * @return 如果郵箱存在返回 true
     */
    boolean existsByEmail(String email);
}
