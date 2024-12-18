package com.roomreservation.repository;

import com.roomreservation.model.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardDao extends JpaRepository<CardEntity, Long> {
  Optional<CardEntity> findByCardNumber(String cardNumber);
}
