package com.telenet.lockmate.service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telenet.lockmate.model.dto.lock.LockDTO;
import com.telenet.lockmate.model.dto.log.LogDTO;
import com.telenet.lockmate.model.entity.Lock;
import com.telenet.lockmate.model.entity.Log;
import com.telenet.lockmate.model.enums.Status;
import com.telenet.lockmate.repository.LockRepository;
import com.telenet.lockmate.repository.LogRepository;
import com.telenet.lockmate.util.AppUtil;

@Service
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;
    private final LockRepository lockRepository;
    private final LogRepository logRepository;

    private static final Pattern LOG_PATTERN = Pattern.compile("^(MAGLOCK-[A-Za-z0-9]+):(.*)$");

    public KafkaConsumerService(SimpMessagingTemplate messagingTemplate,
                                LockRepository lockRepository,
                                LogRepository logRepository) {
        this.messagingTemplate = messagingTemplate;
        this.lockRepository = lockRepository;
        this.logRepository = logRepository;
    }

    @KafkaListener(topics = "maglock-events", groupId = "lockmate-group")
    @Transactional
    public void consume(String message) {
        AppUtil.LOG.info("RECEIVED MSG -> 'maglock-events' :: " + message);

        Matcher matcher = LOG_PATTERN.matcher(message);
        if (matcher.find()) {
            String serialNumber = matcher.group(1);
            String event = matcher.group(2).trim();

            Lock lock = processEvent(serialNumber, event);

            // ✅ Save and broadcast log entry
            Log logEntry = saveLog(lock, serialNumber, event);
            messagingTemplate.convertAndSend("/topic/logs." + serialNumber, LogDTO.from(logEntry));

            // ✅ Send structured lock update to WebSocket
            messagingTemplate.convertAndSend("/topic/status", LockDTO.from(lock));
        }
    }

    private Lock processEvent(String serialNumber, String event) {
        Lock lock = lockRepository.findBySerialNumber(serialNumber)
                .orElseGet(() -> {
                    Lock newLock = Lock.builder()
                            .serialNumber(serialNumber)
                            .dateCreated(LocalDateTime.now())
                            .lastModified(LocalDateTime.now())
                            .isActive(true)
                            .build();
                    return lockRepository.save(newLock);
                });

        // Default: if we get *any* event, the device is online
        lock.setOnline(true);

        if (event.equalsIgnoreCase("CONNECTED")) {
            lock.setStatus(Status.CONNECTED);
            AppUtil.LOG.info(serialNumber + " is now ONLINE");

        } else if (event.equalsIgnoreCase("DISCONNECTED")) {
            lock.setOnline(false);
            lock.setStatus(Status.DISCONNECTED);
            AppUtil.LOG.info(serialNumber + " is now OFFLINE");

        } else if (event.toUpperCase().contains("INPUT HIGH")) {
            lock.setLocked(false);
            lock.setStatus(Status.UNLOCKED);
            AppUtil.LOG.info(serialNumber + " door is now UNLOCKED");

        } else if (event.toUpperCase().contains("INPUT LOW")) {
            lock.setLocked(true);
            lock.setStatus(Status.LOCKED);
            AppUtil.LOG.info(serialNumber + " door is now LOCKED");

        } else if (event.equalsIgnoreCase("Locked by server")) {
            lock.setLocked(true);
            lock.setStatus(Status.LOCKED);
            AppUtil.LOG.info(serialNumber + " was locked by server");

        } else if (event.equalsIgnoreCase("Unlocked by server")) {
            lock.setLocked(false);
            lock.setStatus(Status.UNLOCKED);
            AppUtil.LOG.info(serialNumber + " was unlocked by server");
        }

        lock.setLastModified(LocalDateTime.now());
        return lockRepository.save(lock);
    }

    private Log saveLog(Lock lock, String serialNumber, String event) {
        Log logEntry = Log.builder()
                .message(event)
                .level("INFO")
                .timestamp(LocalDateTime.now().toString())
                .lockId(lock.getId())
                .details("{\"SN\":\"" + serialNumber + "\"}")
                .dateCreated(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build();

        return logRepository.save(logEntry);
    }

}
