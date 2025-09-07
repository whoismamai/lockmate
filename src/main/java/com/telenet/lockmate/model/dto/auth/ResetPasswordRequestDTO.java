package com.telenet.lockmate.model.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResetPasswordRequestDTO {
    private String token;
    private String newPassword;
}
