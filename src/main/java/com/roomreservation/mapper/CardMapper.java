package com.roomreservation.mapper;

import com.roomreservation.model.CardEntity;
import com.roomreservation.record.CardRecord;

/**
 * Utility class for mapping between CardEntity objects and their DTO representations.
 * Provides static conversion methods to transform card entities into CardRecord objects.
 *
 * @see com.roomreservation.model.CardEntity
 * @see com.roomreservation.record.CardRecord
 */
public class CardMapper {
  /**
   * Converts a CardEntity to its DTO representation.
   * Maps the card's identifier, number, and associated user information.
   *
   * @param cardEntity The card entity to convert, must not be null
   * @return CardRecord containing the card's data in DTO format
   * @throws NullPointerException if cardEntity is null
   */
  public static CardRecord of(CardEntity cardEntity) {
    return new CardRecord(
      cardEntity.getId(),
      cardEntity.getCardNumber(),
      cardEntity.getUserEntity().getId()
    );
  }
}
