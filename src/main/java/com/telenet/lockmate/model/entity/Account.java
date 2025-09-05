package com.telenet.lockmate.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "accounts")
@Builder
@Getter
@Setter
public class Account {

    // first account is the system account

    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String name;
    private BigDecimal balance;
    private String ownerId; // Foreign key to User entity

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
    private LocalDate lastBillingDate;
}
