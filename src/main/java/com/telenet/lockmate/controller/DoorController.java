package com.telenet.lockmate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telenet.lockmate.util.AppUtil;

@RestController
@RequestMapping("/api/doors")
public class DoorController {
    
    // for creating and pairing a door lock to a unit

    @PostMapping
    public ResponseEntity<Void> createAndPairDoorLock() {

        // create door lock and pair it to a unit (we expect unitId and lock serial number in the request body or params)

        AppUtil.LOG.info("Creating and pairing new door lock");

        return null;     
    }

    // get all doors ; both the ones paired to locks and the unpaired ones
    // user fetches their own only ; admin fetches all
    @GetMapping
    public ResponseEntity<Void> getAllDoors() {

        AppUtil.LOG.info("Fetching all doors");

        return null;        
    }

    // get door status
    @GetMapping("/{id}/status")
    public ResponseEntity<Void> getDoorLockStatus(@PathVariable String id) {

        AppUtil.LOG.info("Fetching door lock status");

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

    // buzz the door : support the user to identify a door lock by buzzing it
    @PutMapping("/{id}/buzz")
    public ResponseEntity<Void> buzzDoor() {

        AppUtil.LOG.info("Buzzing door");

        return null;
    }
}
