package com.telenet.lockmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/lockmate")
public class CommandController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.commandTopic}")
    private String topicName;

    // === FRONTEND PAGE ===
    @GetMapping
    public String lockmatePage() {
        return "lockmate"; // maps to src/main/resources/templates/lockmate.html
    }

    // === API ENDPOINTS ===
    @ResponseBody
    @PostMapping("/lock")
    public ResponseEntity<String> lockDevice(@RequestParam String MLID) {
        String commandMessage = MLID + ":L";
        kafkaTemplate.send(topicName, commandMessage);
        return ResponseEntity.ok("Lock command sent for device: " + MLID);
    }

    @ResponseBody
    @PostMapping("/unlock")
    public ResponseEntity<String> unlockDevice(@RequestParam String MLID) {
        String commandMessage = MLID + ":U";
        kafkaTemplate.send(topicName, commandMessage);
        return ResponseEntity.ok("Unlock command sent for device: " + MLID);
    }
}
