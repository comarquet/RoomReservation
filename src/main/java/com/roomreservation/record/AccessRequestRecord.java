package com.roomreservation.record;

/**
 * Record representing an access control request for room entry.
 * Used to validate whether a card has access to a specific room.
 *
 * @param cardNumber The unique identifier of the access card attempting entry
 * @param roomId The unique identifier of the room being accessed
 */
public record AccessRequestRecord(String cardNumber, Long roomId) {}
