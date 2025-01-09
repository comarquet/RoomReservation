package com.roomreservation.record;

/**
 * Record representing a login request.
 * Used for user authentication in the system.
 *
 * @param email The user's email address used for authentication
 * @param password The user's password (should be handled securely)
 */
public record LoginRequestRecord(String email, String password) {}