package com.telenet.lockmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.telenet.lockmate.model.dto.lock.LockDTO;
import com.telenet.lockmate.model.entity.Lock;
import com.telenet.lockmate.model.enums.Status;
import com.telenet.lockmate.repository.LockRepository;

import java.time.LocalDateTime;

@Controller
public class LockCommandWSController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private LockRepository lockRepository;

    @Value("${kafka.commandTopic}")
    private String topicName;

    @MessageMapping("/lock")
    @SendTo("/topic/status")
    public LockDTO lockDevice(Message message) {
        String commandMessage = message.getDeviceId() + ":L";
        kafkaTemplate.send(topicName, commandMessage);

        Lock lock = lockRepository.findBySerialNumber(message.getDeviceId())
                .orElseGet(() -> {
                    Lock newLock = Lock.builder()
                            .serialNumber(message.getDeviceId())
                            .dateCreated(LocalDateTime.now())
                            .lastModified(LocalDateTime.now())
                            .isActive(true)
                            .build();
                    return lockRepository.save(newLock);
                });

        lock.setLocked(true);
        lock.setOnline(true);
        lock.setStatus(Status.LOCKED);
        lock.setLastModified(LocalDateTime.now());
        lockRepository.save(lock);

        return LockDTO.from(lock);
    }

    @MessageMapping("/unlock")
    @SendTo("/topic/status")
    public LockDTO unlockDevice(Message message) {
        String commandMessage = message.getDeviceId() + ":U";
        kafkaTemplate.send(topicName, commandMessage);

        Lock lock = lockRepository.findBySerialNumber(message.getDeviceId())
                .orElseGet(() -> {
                    Lock newLock = Lock.builder()
                            .serialNumber(message.getDeviceId())
                            .dateCreated(LocalDateTime.now())
                            .lastModified(LocalDateTime.now())
                            .isActive(true)
                            .build();
                    return lockRepository.save(newLock);
                });

        lock.setLocked(false);
        lock.setOnline(true);
        lock.setStatus(Status.UNLOCKED);
        lock.setLastModified(LocalDateTime.now());
        lockRepository.save(lock);

        return LockDTO.from(lock);
    }

    // === DTOs ===
    public static class Message {
        private String deviceId;
        public String getDeviceId() { return deviceId; }
        public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    }
}
