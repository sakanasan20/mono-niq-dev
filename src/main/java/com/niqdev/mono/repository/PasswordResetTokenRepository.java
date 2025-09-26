package com.niqdev.mono.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niqdev.mono.entity.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("delete from PasswordResetToken t where t.expiresAt < :instant")
    void deleteByExpiresAtBefore(@Param("instant") Instant instant);
}


