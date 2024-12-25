package com.roomreservation.api;

import com.roomreservation.record.AccessRequestRecord;
import com.roomreservation.record.AccessResponseRecord;
import com.roomreservation.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/access")
public class AccessController {
  
  @Autowired
  private AccessService accessService;
  
  @PostMapping
  public ResponseEntity<AccessResponseRecord> validateAccess(@RequestBody AccessRequestRecord requestRecord) {
    AccessResponseRecord responseRecord = accessService.validateAccess(requestRecord);
    return ResponseEntity.ok(responseRecord);
  }
}