package com.telenet.lockmate.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "access_grants")
@Builder
@Getter
@Setter
public class AccessGrant {
    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String accessId;

    private String token; // random, signed, or JWT
    
    private LocalDateTime expiresAt;
    
    private boolean used;
    private LocalDateTime usedAt;

    private boolean singleUse;

    @Default private Integer maxUses = 1; // if > 1 allow reuse

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
}
