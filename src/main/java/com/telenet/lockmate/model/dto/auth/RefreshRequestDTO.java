package com.telenet.lockmate.model.dto.auth;

import lombok.Data;

@Data
public class RefreshRequestDTO {
    private String refreshToken;
}
