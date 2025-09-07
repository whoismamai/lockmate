package com.telenet.lockmate.model.entity;

import java.security.Permission;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
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
@Table(name = "accesses")
@Getter
@Setter
@Builder
public class Access {
    @Id
    @Default private String id = java.util.UUID.randomUUID().toString();

    private String lockId; // Foreign key to Lock entity
    private String granteeId; // Foreign key to User entity (the person who gets access)
    private String granterId; // Foreign key to User entity (the person who grants access

    // permissions granted
    @ElementCollection(targetClass = Permission.class)
    @Enumerated(EnumType.STRING)
    @Default private Set<Permission> permissions = new HashSet<>();

    private boolean canDelegate; // if true, grantee can grant access to others
    private boolean revoked; // if true, access has been revoked or soft-deleted
    private LocalDateTime revokedAt; // timestamp of when access was revoked
    private String revokedBy; // userId of who revoked the access

    private String label; // e.g., "Main Entrance Access"

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;

    // will link to schedules, bookings, grants, or other entities as needed

    private String accessType; // e.g., "PERMANENT", "TEMPORARY", "RECURRING"
    private String accessDetails; // JSON string with additional details (e.g., start/end time for temporary access)
}
