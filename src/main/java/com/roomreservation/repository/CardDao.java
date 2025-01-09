package com.roomreservation.repository;

import com.roomreservation.model.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing access card entities.
 * Provides CRUD operations and queries for access cards.
 * Extends JpaRepository to inherit standard database operations.
 *
 * @see com.roomreservation.model.CardEntity
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface CardDao extends JpaRepository<CardEntity, Long> {
  /**
   * Finds a card by its unique card number.
   *
   * @param cardNumber the unique number of the card to find
   * @return Optional containing the CardEntity if found, empty Optional otherwise
   */
  Optional<CardEntity> findByCardNumber(String cardNumber);
}
