package com.telenet.lockmate.model.dto.auth;

import lombok.Data;

@Data
public class ChangePasswordRequestDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
