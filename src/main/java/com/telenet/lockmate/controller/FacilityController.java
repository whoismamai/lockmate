package com.telenet.lockmate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telenet.lockmate.util.AppUtil;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    // belongs to creator
    @PostMapping
    public ResponseEntity<Void> createFacility() {
        
        AppUtil.LOG.info("Creating new facility");

        return null;
    }

    // only by creator
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFacility() {

        AppUtil.LOG.info("Updating facility");

        return null;
    }

    // by creator or admin
    @GetMapping("/{id}")
    public ResponseEntity<Void> getFacilityById() {

        AppUtil.LOG.info("Fetching facility by ID");

        return null;
    }

    // by admin - get all facilities
    // by user - get facilities assigned to user
    @GetMapping
    public ResponseEntity<Void> getAllFacilities() {

        AppUtil.LOG.info("Fetching all facilities");

        return null;
    }

    // only by creator or admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacility() {

        AppUtil.LOG.info("Deleting facility");

        return null;
    }
    
    // get units belonging to a facility
    public ResponseEntity<Void> getUnitsByFacility() {

        AppUtil.LOG.info("Fetching units for facility");

        return null;
    }

    // get locks belonging to a facility
    public ResponseEntity<Void> getLocksByFacility() {

        AppUtil.LOG.info("Fetching locks for facility");

        return null;
    }
}
