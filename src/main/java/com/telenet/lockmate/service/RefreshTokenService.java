package com.telenet.lockmate.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telenet.lockmate.model.entity.RefreshToken;
import com.telenet.lockmate.model.entity.User;
import com.telenet.lockmate.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final long refreshTokenDurationMs;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService, @Value("${jwt.refreshTokenExpirationMs}") long refreshTokenDurationMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.refreshTokenDurationMs = refreshTokenDurationMs;
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        String token = jwtService.generateRefreshToken(user.getEmailAddress());
        RefreshToken refreshToken = RefreshToken.builder()
            .token(token)
            .userId(user.getId())
            .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
            .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new sign-in request");
        }

        return token;
    }

    @Transactional
    public int deleteByUser(String userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
