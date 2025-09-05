package com.telenet.lockmate.model.enums;

public enum Status {
    LOCKED, // For locks that are currently locked
    UNLOCKED, // For locks that are currently unlocked
    MAINTENANCE, // For locks that are under maintenance
    OFFLINE, // For locks that are not reachable

    CONNECTED, // When connects
    DISCONNECTED, // When connects

    BOOKED, // For units that are currently booked
    AVAILABLE, // For units that are available for booking
    CHECKED_IN, // For units that are currently checked in
    CHECKED_OUT, // For units that are currently checked out
    CLEANING // For units that are being cleaned
}