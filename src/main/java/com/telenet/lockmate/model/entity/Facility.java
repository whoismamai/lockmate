package com.telenet.lockmate.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "facilities")
@Getter
@Setter
@Builder
public class Facility {
    @Id
    @Default private String id = UUID.randomUUID().toString();

    private String name;
    private String physicalAddress;
    private String contactNumber;
    private String emailAddress;
    private String ownerId; // Foreign key to User entity

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
}