package com.telenet.lockmate.model.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class LoginResponseDTO {
    private String userId;
    private String fullName;
    private String email;
    private String accessToken;
    private String refreshToken;
    private Instant expiresAt;
}
