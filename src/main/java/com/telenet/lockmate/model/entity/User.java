package com.telenet.lockmate.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.telenet.lockmate.model.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
public class User {

    // Builder.Default
    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String fullName;
    private String emailAddress;
    private String physicalAddress;
    private String phoneNumber;
    
    @Column(columnDefinition = "TEXT")
    private String password;

    @Enumerated(EnumType.STRING)
    @Default private UserType userType = UserType.USER;

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
    private LocalDateTime lastLogin;

    private boolean isOnline;
    private boolean isActive;
}