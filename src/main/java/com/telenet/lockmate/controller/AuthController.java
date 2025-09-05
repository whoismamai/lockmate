package com.telenet.lockmate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telenet.lockmate.model.dto.auth.UserLoginDTO;
import com.telenet.lockmate.model.dto.auth.UserRegisterDTO;
import com.telenet.lockmate.util.AppUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    // auth methods (login, logout, register)

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login() {

        AppUtil.LOG.info("User login attempt");

        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> register() {

        AppUtil.LOG.info("User registration attempt");

        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {

        AppUtil.LOG.info("User logout attempt");

        return null;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() {
        AppUtil.LOG.info("Token refresh attempt");

        return null;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword() {

        AppUtil.LOG.info("Password reset request");

        return null;
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword() {

        AppUtil.LOG.info("Password change request");

        return null;
    }
}
