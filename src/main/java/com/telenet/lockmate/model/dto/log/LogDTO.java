package com.telenet.lockmate.model.dto.log;

import java.time.LocalDateTime;
import com.telenet.lockmate.model.entity.Log;

public record LogDTO(
        String serialNumber,
        String message,
        String level,
        LocalDateTime dateCreated
) {
    public static LogDTO from(Log log) {
        // serialNumber is stored in JSON details — OR fetch from the Lock
        String serialNumber = log.getLockId();
        if (log.getDetails() != null && log.getDetails().contains("serialNumber")) {
            serialNumber = log.getDetails()
                              .replace("{\"serialNumber\":\"", "")
                              .replace("\"}", "");
        }

        return new LogDTO(
                serialNumber,
                log.getMessage(),
                log.getLevel(),
                log.getDateCreated()
        );
    }
}
