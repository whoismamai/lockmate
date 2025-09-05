package com.telenet.lockmate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telenet.lockmate.util.AppUtil;

@RestController
@RequestMapping("/api/locks")
public class LockController {
    
    // for creating and pairing a door lock to a unit

    // get all locks ; both the ones paired to doors and the unpaired ones
    @GetMapping
    public ResponseEntity<Void> getAllLocks() {

        AppUtil.LOG.info("Fetching all door locks");

        return null;        
    }

    // by authorized user or admin
    @PutMapping("/{id}/lock")
    public ResponseEntity<Void> lockDoor() {

        AppUtil.LOG.info("Locking door");

        return null;
    }

    // by authorized user or admin
    @PutMapping("/{id}/unlock")
    public ResponseEntity<Void> unlockDoor() {

        AppUtil.LOG.info("Unlocking door");

        return null;
    }

    // view door history : access logs, lock/unlock events
    // by authorized user or admin
    @GetMapping("/{id}/history")
    public ResponseEntity<Void> getDoorHistory() {

        AppUtil.LOG.info("Fetching door history");

        return null;
    }
}
