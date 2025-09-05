package com.telenet.lockmate.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.telenet.lockmate.model.enums.AccountType;

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
@Table(name = "accounts")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    // first account is the system account
    // facilities have internal accounts. the owner of the facility withdraws from the facility account to their wallet
    // this is an actual money transfer

    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String name;
    private BigDecimal balance;
    private String ownerId; // Foreign key to User entity

    @Column(columnDefinition = "TEXT", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType; // MPESA, BANK, PAYPAL, INTERNAL

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
    private LocalDate lastBillingDate;
}
