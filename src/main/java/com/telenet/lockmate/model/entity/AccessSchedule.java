package com.telenet.lockmate.model.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.telenet.lockmate.model.enums.ScheduleType;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder.Default;

@Entity
@Table(name = "access_schedules")
@Builder
@Getter
@Setter
public class AccessSchedule {

    @Id
    @Default private String id = java.util.UUID.randomUUID().toString();
    
    private String acceddId; // Foreign key to Access entity
    private ScheduleType scheduleType; // ONE_TIME, RECURRING

    // for ONE_TIME
    private LocalDateTime startAt; // exact start date and time
    private LocalDateTime endAt; // exact end date and time

    // for RECURRING
    @ElementCollection(targetClass = DayOfWeek.class)
    @Default private java.util.Set<DayOfWeek> daysOfWeek = new java.util.HashSet<>();
    
    private LocalTime fromTime; // time of day when access starts
    private LocalTime toTime; // time of day when access ends

    private String timezone; // optional timezone string if we want to store per tz

    private boolean singleUse; // may mark access as consumed after use

    private LocalDateTime dateCreated;
    private LocalDateTime lastModified;
}
