package com.telenet.lockmate.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.telenet.lockmate.model.enums.Status;

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
@Table(name = "bookings")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @Default private String id = java.util.UUID.randomUUID().toString();

    private String unitId; // Foreign key to Unit entity

    @Column(updatable = false, nullable = false)
    private String userId; // Foreign key to User entity

    private int period; // in days

    @Column(updatable = true, nullable = false)
    private LocalDateTime bookingDate; // date when the booking was confirmed

    @Column(updatable = true, nullable = false)
    private LocalDate checkinDate;

    @Column(updatable = true, nullable = false)
    private LocalDate checkoutDate;

    @Enumerated(EnumType.STRING)
    @Column(updatable = true, nullable = false)
    private Status status; // BOOKED, CHECKED_IN, CHECKED_OUT, CANCELLED

    @Column(updatable = false, nullable = false)
    private String reference; // payment reference or transaction id

    @Column(updatable = false, nullable = false)
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    private LocalDateTime lastModified;
}
