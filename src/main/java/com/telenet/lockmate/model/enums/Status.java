package com.telenet.lockmate.model.enums;

public enum Status {
    LOCKED, // For locks that are currently locked
    UNLOCKED, // For locks that are currently unlocked
    MAINTENANCE, // For locks that are under maintenance
    OFFLINE // For locks that are not reachable
}