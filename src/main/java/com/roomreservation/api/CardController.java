package com.roomreservation.api;

import com.roomreservation.mapper.CardMapper;
import com.roomreservation.model.CardEntity;
import com.roomreservation.record.CardCommandRecord;
import com.roomreservation.record.CardRecord;
import com.roomreservation.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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
  
  @PostMapping("/assign/{userId}")
  public ResponseEntity<CardRecord> assignCardToUser(
    @PathVariable Long userId,
    @RequestBody CardCommandRecord cardCommand) {
    CardEntity card = cardService.assignCardToUser(userId, cardCommand);
    return ResponseEntity.ok(CardMapper.of(card));
  }
  
  @PostMapping("/deactivate/{userId}")
  public ResponseEntity<Void> deactivateCard(@PathVariable Long userId) {
    cardService.deactivateCard(userId);
    return ResponseEntity.noContent().build();
  }
}