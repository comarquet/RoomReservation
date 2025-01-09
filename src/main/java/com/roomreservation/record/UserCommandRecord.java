package com.roomreservation.record;

/**
 * Record representing a command to create or update a user.
 * Contains the essential information needed for user management.
 *
 * @param firstName The user's first name
 * @param lastName The user's last name
 * @param email The user's email address (must be unique in the system)
 * @param password The user's password (should be handled securely)
 */
public record UserCommandRecord(
  String firstName,
  String lastName,
  String email,
  String password
) {}
