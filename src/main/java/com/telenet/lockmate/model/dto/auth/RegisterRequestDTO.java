package com.telenet.lockmate.model.dto.auth;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String fullName;
    private String email;
    private String password;
}