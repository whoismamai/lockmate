package com.telenet.lockmate.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.telenet.lockmate.model.dto.lock.LockDTO;
import com.telenet.lockmate.model.enums.Status;
import com.telenet.lockmate.service.LockService;
import com.telenet.lockmate.util.AppUtil;

@RestController
@RequestMapping("/api/locks")
public class LockController {

    private final LockService lockService;

    public LockController(LockService lockService) {
        this.lockService = lockService;
    }

    @GetMapping
    public ResponseEntity<List<LockDTO>> getAllLocks() {
        AppUtil.LOG.info("Fetching all door locks");
        return ResponseEntity.ok(lockService.getAllLocks());
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<LockDTO> lockDoor(@PathVariable String id) {
        AppUtil.LOG.info("Locking door {}", id);
        LockDTO updated = lockService.updateLockStatus(id, Status.LOCKED);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/unlock")
    public ResponseEntity<LockDTO> unlockDoor(@PathVariable String id) {
        AppUtil.LOG.info("Unlocking door {}", id);
        LockDTO updated = lockService.updateLockStatus(id, Status.UNLOCKED);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<String> getDoorHistory(@PathVariable String id) {
        AppUtil.LOG.info("Fetching door history for {}", id);
        return ResponseEntity.ok("History for lock " + id);
    }

    @PutMapping("/{id}/buzz")
    public ResponseEntity<String> buzzDoor(@PathVariable String id) {
        AppUtil.LOG.info("Buzzing door {}", id);
        return ResponseEntity.ok("Buzzed door " + id);
    }
}
