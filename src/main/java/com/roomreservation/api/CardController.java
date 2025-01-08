package com.roomreservation.api;

import com.roomreservation.record.CardRecord;
import com.roomreservation.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
  
  private final CardService cardService;
  
  public CardController(CardService cardService) {
    this.cardService = cardService;
  }
  
  @GetMapping
  public ResponseEntity<List<CardRecord>> getAllCards() {
    return ResponseEntity.ok(cardService.getAllCards());
  }
  
}