package com.roomreservation.record;

/**
 * Record representing a user in the system.
 * Contains complete user information including associated access card.
 *
 * @param id The unique identifier of the user
 * @param firstName The user's first name
 * @param lastName The user's last name
 * @param email The user's email address
 * @param password The user's password (hashed)
 * @param cardId The unique identifier of the user's associated access card (null if no card assigned)
 */
public record UserRecord(
  Long id,
  String firstName,
  String lastName,
  String email,
  String password,
  Long cardId
) {}
