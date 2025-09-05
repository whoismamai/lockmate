package com.telenet.lockmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.telenet.lockmate.model.enums.Status;

@Controller
public class LockCommandWSController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.commandTopic}")
    private String topicName;

    @MessageMapping("/lock")
    @SendTo("/topic/status")
    public StatusMessage lockDevice(Message message) {
        String commandMessage = message.getDeviceId() + ":L";
        kafkaTemplate.send(topicName, commandMessage);
        return new StatusMessage(message.getDeviceId(), Status.LOCKED);
    }

    @MessageMapping("/unlock")
    @SendTo("/topic/status")
    public StatusMessage unlockDevice(Message message) {
        String commandMessage = message.getDeviceId() + ":U";
        kafkaTemplate.send(topicName, commandMessage);
        return new StatusMessage(message.getDeviceId(), Status.UNLOCKED);
    }

    // === DTOs ===
    public static class Message {
        private String deviceId;
        public String getDeviceId() { return deviceId; }
        public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    }

    public static class StatusMessage {
        private String deviceId;
        private Status status;
        public StatusMessage(String deviceId, Status status) {
            this.deviceId = deviceId;
            this.status = status;
        }
        public String getDeviceId() { return deviceId; }
        public Status getStatus() { return status; }
    }
}
