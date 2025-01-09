package com.roomreservation.record;

/**
 * Record representing an access card in the system.
 * Contains complete card information including user association.
 *
 * @param id The unique identifier of the card in the system
 * @param cardNumber The unique identifier printed on the physical card
 * @param userId The unique identifier of the user to whom the card is assigned
 */
public record CardRecord(
  Long id,
  String cardNumber,
  Long userId
) {}
