package com.telenet.lockmate.model.dto.lock;

import java.time.LocalDateTime;

import com.telenet.lockmate.model.entity.Lock;
import com.telenet.lockmate.model.enums.Status;

public record LockDTO(
            String id,
            String serialNumber,
            boolean isLocked,
            boolean isOnline,
            Status status,
            LocalDateTime lastModified
    ) {
        public static LockDTO from(Lock lock) {
            return new LockDTO(
                    lock.getId(),
                    lock.getSerialNumber(),
                    lock.isLocked(),
                    lock.isOnline(),
                    lock.getStatus(),
                    lock.getLastModified()
            );
        }
    }