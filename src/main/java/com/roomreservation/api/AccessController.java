package com.roomreservation.api;

import com.roomreservation.record.AccessRequestRecord;
import com.roomreservation.record.AccessResponseRecord;
import com.roomreservation.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing room access validation.
 * This controller handles requests to validate card access to specific rooms.
 * It provides endpoints for checking whether a user's card has permission to access a room.
 */
@RestController
@RequestMapping("/access")
public class AccessController {
  
  @Autowired
  private AccessService accessService;
  
  /**
   * Validates whether a card has access to a specific room.
   * Access is granted if the card holder has an active booking for the room at the current time.
   *
   * @param requestRecord The access request containing card number and room ID
   * @return ResponseEntity containing AccessResponseRecord indicating if access is granted
   * @see AccessRequestRecord
   * @see AccessResponseRecord
   */
  @PostMapping
  public ResponseEntity<AccessResponseRecord> validateAccess(@RequestBody AccessRequestRecord requestRecord) {
    AccessResponseRecord responseRecord = accessService.validateAccess(requestRecord);
    return ResponseEntity.ok(responseRecord);
  }
}