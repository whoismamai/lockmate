package com.telenet.lockmate.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {
    @Id
    @Default private String id = java.util.UUID.randomUUID().toString();

    private String message;
    private String level; // e.g., INFO, WARN, ERROR
    private String timestamp; // ISO 8601 format
    private String userId; // Foreign key to User entity (if applicable)
    private String lockId; // Foreign key to Lock entity (if applicable)

    @Column(columnDefinition = "TEXT")
    private String details; // Additional details or context (JSON string)

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
}
