package com.telenet.lockmate.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.telenet.lockmate.model.entity.User;
import com.telenet.lockmate.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository; // implement repo to find by email

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAddress(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        // Build a Spring Security User; you can adapt authorities as needed
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmailAddress())
                .password(user.getPassword()) // ensure passwords are encoded
                .roles(user.getUserType().name()) // simple mapping
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }
}
