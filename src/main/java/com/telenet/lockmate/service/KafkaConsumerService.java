package com.telenet.lockmate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.telenet.lockmate.util.AppUtil;

@Service
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;

    @Value("${kafka.commandTopic}")
    private String commandTopic;

    public KafkaConsumerService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Listen to the events topic
    @KafkaListener(topics = "maglock-events", groupId = "lockmate-group")
    public void consume(String message) {
        // Example message format: {"deviceId":"ML123","status":"LOCKED"}
        // Forward the message to all connected websocket clients

        AppUtil.LOG.info("RECEIVED MSG -> 'maglock-events' :: " + message);

        messagingTemplate.convertAndSend("/topic/status", message);
    }
}
