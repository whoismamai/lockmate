package com.telenet.lockmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telenet.lockmate.model.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    // Custom query methods can be defined here if needed
}
