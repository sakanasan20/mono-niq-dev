package com.niqdev.mono.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niqdev.mono.entity.PasswordResetToken;
import com.niqdev.mono.entity.User;
import com.niqdev.mono.repository.PasswordResetTokenRepository;
import com.niqdev.mono.repository.UserRepository;

@Service
public class PasswordResetService {

    private static final Duration DEFAULT_TTL = Duration.ofHours(1);
    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String createResetToken(String usernameOrEmail) {
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new IllegalArgumentException("用戶不存在"));

        // 清理過期 token
        tokenRepository.deleteByExpiresAtBefore(Instant.now());

        // 生成安全隨機 token（128-bit -> Base64 URL safe）
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        String tokenValue = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        PasswordResetToken token = new PasswordResetToken(tokenValue, user, Instant.now().plus(DEFAULT_TTL));
        tokenRepository.save(token);

        return tokenValue;
    }

    @Transactional(readOnly = true)
    public PasswordResetToken validateToken(String tokenValue) {
        PasswordResetToken token = tokenRepository.findByToken(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("重設連結無效"));

        if (token.isUsed()) {
            throw new IllegalStateException("重設連結已被使用");
        }
        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalStateException("重設連結已過期");
        }
        return token;
    }

    @Transactional
    public void resetPassword(String tokenValue, String newRawPassword) {
        PasswordResetToken token = validateToken(tokenValue);
        User user = token.getUser();

        user.setPassword(passwordEncoder.encode(newRawPassword));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);
    }
}


