package com.telenet.lockmate.model.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class RegisterResponseDTO {
    private String userId;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;
}
