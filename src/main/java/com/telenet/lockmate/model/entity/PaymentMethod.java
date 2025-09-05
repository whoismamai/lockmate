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
@Table(name = "payment_methods")
@Builder
@Setter
@Getter
public class PaymentMethod {

    // this entity will our payment methods like MPESA, Credit Card, PayPal, etc.
    // For simplicity, we will just store the name of the payment method and its details as a JSON string.

    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String name; // e.g., "MPESA", "Credit Card", "PayPal"
    private String mpesaConsumerKey; // For MPESA
    private String mpesaConsumerSecret; // For MPESA
    private String mpesaShortCode; // For MPESA
    private String mpesaPassKey; // For MPESA
    private String details; // JSON string to store other details if needed

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
}
