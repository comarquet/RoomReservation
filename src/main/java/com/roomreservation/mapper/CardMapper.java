package com.roomreservation.mapper;

import com.roomreservation.model.CardEntity;
import com.roomreservation.record.CardRecord;

public class CardMapper {
  public static CardRecord of(CardEntity cardEntity) {
    return new CardRecord(
      cardEntity.getId(),
      cardEntity.getCardNumber(),
      cardEntity.getUserEntity().getId()
    );
  }
}
