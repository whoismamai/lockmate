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
@RequestMapping("/api/bookings")
public class BookingController {

    // CRUD for booking a facility unit
    @GetMapping
    public ResponseEntity<Void> getAllBookings() {
        
        AppUtil.LOG.info("Fetching all bookings");

        return null;
    }

    @PostMapping
    public ResponseEntity<Void> createBooking() {

        // unitId + userId + time slot + date

        AppUtil.LOG.info("Creating new booking");

        return null;
    }

    // get a given booking by id
    @GetMapping("/{id}")
    public ResponseEntity<Void> getBookingById() {

        AppUtil.LOG.info("Fetching booking by ID");

        return null;
    }

    // update a booking (reschedule, cancel)
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBooking() {

        AppUtil.LOG.info("Updating booking");

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking() {

        AppUtil.LOG.info("Deleting booking");

        return null;
    }
}
