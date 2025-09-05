package com.telenet.lockmate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telenet.lockmate.model.entity.User;
import com.telenet.lockmate.util.AppUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Implementation here

        AppUtil.LOG.info("Fetching user with ID: " + id);

        return null;
    }

    @GetMapping
    public ResponseEntity<User> getAllUsers() {
        // Implementation here

        AppUtil.LOG.info("Fetching all users");

        return null;
    }

    @PostMapping
    public ResponseEntity<User> createUser(User user) {
        // Implementation here

        AppUtil.LOG.info("Creating new user: " + user.getEmailAddress());

        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, User user) {
        // Implementation here

        AppUtil.LOG.info("Updating user with ID: " + id);

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Implementation here

        AppUtil.LOG.info("Deleting user with ID: " + id);

        return null;
    }
}
