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
@RequestMapping("/api/units")
public class UnitController {
    
    // CRUD operations for units

    @PostMapping
    public ResponseEntity<Void> createUnit() {

        // we expect facilityId in the request body or params to link unit to a facility
        
        AppUtil.LOG.info("Creating new unit");

        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> getUnitById() {

        AppUtil.LOG.info("Fetching unit by ID");

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUnit() {

        AppUtil.LOG.info("Updating unit");

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit() {

        AppUtil.LOG.info("Deleting unit");

        return null;
    }
}
