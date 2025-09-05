package com.telenet.lockmate.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Immutable;

import com.telenet.lockmate.model.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "transactions")
@Builder
@Getter
@Setter
@Immutable
public class Transaction {

    // (Optional) run the following on SQL shell to restrict access to the user ; e.g., lm_user
    // REVOKE DELETE ON transactions FROM lm_user;
    // REVOKE UPDATE ON transactions FROM lm_user;

    @Id
    @Column(updatable = false, nullable = false)
    @Default private String id = UUID.randomUUID().toString();

    @Column(updatable = false, nullable = false)
    private String accountId; // Foreign key to Account entity
    @Column(updatable = false, nullable = false)
    private String paymentMethodId; // Foreign key to PaymentMethod entity
    @Column(updatable = false, nullable = false)
    private BigDecimal amount;
    @Column(updatable = false, nullable = false)
    private BigDecimal balanceAfter; // Balance after this transaction
    @Column(updatable = false, nullable = false)
    private String reference; // e.g., MPESA transaction code
    @Column(updatable = false, nullable = false)
    private String initiatedBy; // Foreign key to User entity
    @Column(updatable = true, nullable = false)
    private Status status; // PENDING, COMPLETED, FAILED
    @Column(updatable = false, nullable = true)
    private String failureReason; // If status is FAILED, store the reason here
    @Column(updatable = false, nullable = false)
    private String description;

    @Column(updatable = false, nullable = false)
    private LocalDateTime dateCreated;
    @Column(updatable = false, nullable = false)
    private LocalDateTime lastModified;
}
