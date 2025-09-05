package com.telenet.lockmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telenet.lockmate.model.entity.Lock;

public interface LockRepository extends JpaRepository<Lock, String> {

    // Custom query methods can be defined here if needed
    Optional<Lock> findBySerialNumber(String serialNumber);    
}
