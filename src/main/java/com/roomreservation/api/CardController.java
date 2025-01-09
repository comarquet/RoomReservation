package com.roomreservation.api;

import com.roomreservation.record.CardRecord;
import com.roomreservation.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for managing access cards.
 * Provides endpoints for retrieving and managing card information.
 */
@RestController
@RequestMapping("/api/cards")
public class CardController {
  
  private final CardService cardService;
  
  public CardController(CardService cardService) {
    this.cardService = cardService;
  }
  
  /**
   * Retrieves all cards in the system.
   *
   * @return ResponseEntity containing list of all cards
   * @see CardRecord
   */
  @GetMapping
  public ResponseEntity<List<CardRecord>> getAllCards() {
    return ResponseEntity.ok(cardService.getAllCards());
  }
  
}