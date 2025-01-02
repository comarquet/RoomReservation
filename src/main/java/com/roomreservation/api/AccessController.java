package com.roomreservation.api;

import com.roomreservation.record.AccessRequestRecord;
import com.roomreservation.record.AccessResponseRecord;
import com.roomreservation.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller handling room access validation requests.
 * Provides endpoints for validating card access to rooms.
 */
@CrossOrigin
@RestController
@RequestMapping("/access")
public class AccessController {
  
  @Autowired
  private AccessService accessService;
  
  /**
   * Validates whether a card has access to a specific room.
   *
   * @param requestRecord Contains the card number and room ID to validate
   * @return ResponseEntity with AccessResponseRecord indicating if access is granted
   */
  @PostMapping
  public ResponseEntity<AccessResponseRecord> validateAccess(@RequestBody AccessRequestRecord requestRecord) {
    AccessResponseRecord responseRecord = accessService.validateAccess(requestRecord);
    return ResponseEntity.ok(responseRecord);
  }
}