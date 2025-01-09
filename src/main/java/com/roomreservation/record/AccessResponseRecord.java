package com.roomreservation.record;

/**
 * Record representing the response to an access control request.
 * Indicates whether access is granted or denied.
 *
 * @param accessGranted true if access is granted, false if access is denied
 */
public record AccessResponseRecord(boolean accessGranted) {}