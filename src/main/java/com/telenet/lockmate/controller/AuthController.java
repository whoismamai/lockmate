package com.telenet.lockmate.controller;

import com.telenet.lockmate.model.dto.auth.ChangePasswordRequestDTO;
import com.telenet.lockmate.model.dto.auth.ForgotPasswordRequestDTO;
import com.telenet.lockmate.model.dto.auth.LoginRequestDTO;
import com.telenet.lockmate.model.dto.auth.LoginResponseDTO;
import com.telenet.lockmate.model.dto.auth.RefreshRequestDTO;
import com.telenet.lockmate.model.dto.auth.RegisterRequestDTO;
import com.telenet.lockmate.model.dto.auth.RegisterResponseDTO;
import com.telenet.lockmate.model.dto.auth.ResetPasswordRequestDTO;
import com.telenet.lockmate.model.entity.RefreshToken;
import com.telenet.lockmate.model.entity.User;
import com.telenet.lockmate.repository.UserRepository;
import com.telenet.lockmate.service.JwtService;
import com.telenet.lockmate.service.MailService;
import com.telenet.lockmate.service.PasswordResetService;
import com.telenet.lockmate.service.RefreshTokenService;
import com.telenet.lockmate.util.AppUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordResetService passwordResetService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {
        AppUtil.LOG.info("User login attempt: {}", req.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User user = userRepository.findByEmailAddress(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update last login timestamp
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user.getEmailAddress());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        LoginResponseDTO dto = LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiresAt(jwtService.extractExpiration(accessToken).toInstant())
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmailAddress())
                .build();

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO req) {
        AppUtil.LOG.info("User registration attempt: {}", req.getEmail());

        if (userRepository.findByEmailAddress(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .fullName(req.getFullName())
                .emailAddress(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .isActive(true)
                .dateCreated(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build();

        userRepository.save(user);

        RegisterResponseDTO dto = RegisterResponseDTO.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmailAddress())
                .createdAt(user.getDateCreated())
                .build();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String refreshToken) {
        AppUtil.LOG.info("User logout attempt");

        refreshTokenService.deleteByToken(refreshToken);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequestDTO request) {
        AppUtil.LOG.info("Token refresh attempt");

        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(rt -> {
                    User user = userRepository.findById(rt.getUserId()).orElseThrow();
                    // rotate refresh token
                    refreshTokenService.deleteByToken(request.getRefreshToken());
                    RefreshToken newRt = refreshTokenService.createRefreshToken(user);

                    String newAccessToken = jwtService.generateAccessToken(user.getEmailAddress());

                    return ResponseEntity.ok(Map.of(
                            "accessToken", newAccessToken,
                            "refreshToken", newRt.getToken(),
                            "expiresAt", jwtService.extractExpiration(newAccessToken).toInstant()
                    ));
                })
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDTO request) {
        AppUtil.LOG.info("Password reset request for {}", request.getEmail());

        User user = userRepository.findByEmailAddress(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = passwordResetService.createPasswordResetToken(user.getEmailAddress());

        mailService.sendPasswordResetEmail(user.getEmailAddress(), token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/reset-password")
    public ModelAndView resetPasswordPage(@RequestParam("token") String token) {
        ModelAndView mv = new ModelAndView("reset-password"); // Thymeleaf template
        mv.addObject("token", token);
        return mv;
    }

    @PostMapping("/reset-password/form")
    public String resetPasswordForm(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword) {

        ResetPasswordRequestDTO dto = new ResetPasswordRequestDTO();
        dto.setToken(token);
        dto.setNewPassword(newPassword);

        this.resetPassword(dto); // reuse your existing endpoint logic
        return "reset-success"; // a simple Thymeleaf confirmation page
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        AppUtil.LOG.info("Password reset attempt with token {}", request.getToken());

        var resetToken = passwordResetService.validateToken(request.getToken());

        User user = userRepository.findByEmailAddress(resetToken.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setLastModified(LocalDateTime.now());
        userRepository.save(user);

        // invalidate token after use
        passwordResetService.consumeToken(request.getToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequestDTO request) {
        AppUtil.LOG.info("Password change request for {}", request.getEmail());

        User user = userRepository.findByEmailAddress(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setLastModified(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
