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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

    private String profileImageUrl;
    
    private String accountId; // Foreign key to Account entity

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
    private LocalDateTime lastLogin;

    private boolean isOnline;
    private boolean isActive;
}