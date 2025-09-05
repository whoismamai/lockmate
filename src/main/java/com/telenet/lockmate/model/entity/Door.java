package com.telenet.lockmate.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.telenet.lockmate.model.enums.Status;

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
@Table(name = "doors")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Door {
    @Id
    @Default private String id = UUID.randomUUID().toString();
    
    private String name;
    private String lockId; // Foreign key to Lock entity
    private String unitId; // Foreign key to Unit entity

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;

    // unused fields from Lock entity
    private Status status; // adapted from lock's status
    private String location; // adapted from lock's location
}
