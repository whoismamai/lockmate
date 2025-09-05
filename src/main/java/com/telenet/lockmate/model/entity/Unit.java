package com.telenet.lockmate.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "units")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String name;
    private String facilityId; // Foreign key to Facility entity
    private String lockId; // Foreign key to Lock entity
    private String description;
    
    private String address; // Physical address of the unit
    private int capacity; // Number of people the unit can accommodate

    private BigDecimal pricePerDay;
    private BigDecimal pricePerHour;

    private boolean isAvailable;

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
}
