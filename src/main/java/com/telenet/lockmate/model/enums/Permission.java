package com.telenet.lockmate.model.enums;

public enum Permission {
    OPEN, // Permission to open locks
    CONFIGURE, // Permission to configure lock settings
    GRANT, // Permission to grant access to other users. can revoke the one they granted
    FORCE_OPEN, // Permission to force open locks (e.g., in emergencies)
}
