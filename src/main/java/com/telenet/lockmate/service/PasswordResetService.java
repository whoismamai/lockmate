package com.telenet.lockmate.service;

import com.telenet.lockmate.model.entity.PasswordResetToken;
import com.telenet.lockmate.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;

    @Transactional
    public String createPasswordResetToken(String email) {
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .email(email)
                .token(token)
                .expiryDate(Instant.now().plus(15, ChronoUnit.MINUTES))
                .build();

        tokenRepository.save(resetToken);
        return token;
    }

    public PasswordResetToken validateToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(Instant.now()))
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));
    }

    @Transactional
    public void consumeToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}
