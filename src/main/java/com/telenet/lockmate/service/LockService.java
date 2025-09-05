package com.telenet.lockmate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.telenet.lockmate.model.dto.lock.LockDTO;
import com.telenet.lockmate.model.enums.Status;
import com.telenet.lockmate.repository.LockRepository;

@Service
public class LockService {

    private final LockRepository lockRepository;

    public LockService(LockRepository lockRepository) {
        this.lockRepository = lockRepository;
    }

    public List<LockDTO> getAllLocks() {
        return lockRepository.findAll()
                             .stream()
                             .map(LockDTO::from)
                             .collect(Collectors.toList());
    }

    public LockDTO updateLockStatus(String id, Status status) {
        return lockRepository.findById(id)
                .map(lock -> {
                    lock.setStatus(status);
                    lock.setLocked(status == Status.LOCKED);
                    lock.setLastModified(java.time.LocalDateTime.now());
                    lockRepository.save(lock);
                    return LockDTO.from(lock);
                })
                .orElse(null);
    }
}
