package com.roomreservation.service;

import com.roomreservation.mapper.CardMapper;
import com.roomreservation.record.CardRecord;
import com.roomreservation.repository.CardDao;
import com.roomreservation.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service managing access card operations in the system.
 * Handles card assignment and management for users.
 */
@Service
public class CardService {
  
  private final CardDao cardDao;
  
  public CardService(CardDao cardDao) {
    this.cardDao = cardDao;
  }
  
  /**
   * Retrieves all cards in the system.
   *
   * @return List of CardRecord representing all cards
   */
  public List<CardRecord> getAllCards() {
    return cardDao.findAll().stream()
      .map(CardMapper::of)
      .collect(Collectors.toList());
  }
}
