package com.roomreservation.api;

import com.roomreservation.record.CardRecord;
import com.roomreservation.model.CardEntity;
import com.roomreservation.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
public class CardController {
  
  private final CardService cardService;
  
  public CardController(CardService cardService) {
    this.cardService = cardService;
  }
  
  @PostMapping("/assign/{userId}")
  public ResponseEntity<CardEntity> assignCardToUser(@PathVariable Long userId, @RequestBody CardRecord CardRecord) {
    return ResponseEntity.ok(cardService.assignCardToUser(userId, CardRecord));
  }
  
  @PostMapping("/deactivate/{userId}")
  public ResponseEntity<Void> deactivateCard(@PathVariable Long userId) {
    cardService.deactivateCard(userId);
    return ResponseEntity.noContent().build();
  }
}
