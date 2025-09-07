package com.telenet.lockmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telenet.lockmate.model.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUserId(String userId);
    void deleteByToken(String token);
}
