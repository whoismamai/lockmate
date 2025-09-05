package com.telenet.lockmate.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.telenet.lockmate.model.enums.Status;

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
@Table(name = "locks")
@Getter
@Setter
@Builder
public class Lock {

    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String serialNumber;
    private String model;
    private String firmwareVersion;
    private String location;
    
    private String ownerId; // Foreign key to User entity
    
    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
    private LocalDate lastMaintenanceDate;

    private int autoLockTimeout; // in seconds
    private int buzzerTimeout; // in seconds

    private boolean buzzerEnabled;
    private boolean exitButtonEnabled;
    private boolean isLocked;
    private boolean isOnline;
    private boolean isActive;
    private boolean hasScheduledLocking;

    @Column(columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    private Status status;
}
