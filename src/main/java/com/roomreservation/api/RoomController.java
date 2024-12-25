package com.roomreservation.api;

import com.roomreservation.record.RoomRecord;
import com.roomreservation.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
  
  private final RoomService roomService;
  
  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }
  
  @GetMapping
  public ResponseEntity<List<RoomRecord>> getAllRooms() {
    return ResponseEntity.ok(roomService.getAllRooms());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<RoomRecord> getRoomById(@PathVariable Long id) {
    return ResponseEntity.ok(roomService.getRoomById(id));
  }
  
  @PostMapping
  public ResponseEntity<RoomRecord> createRoom(@RequestBody RoomRecord roomCommand) {
    return ResponseEntity.ok(roomService.createRoom(roomCommand));
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<RoomRecord> updateRoom(@PathVariable Long id, @RequestBody RoomRecord roomCommand) {
    return ResponseEntity.ok(roomService.updateRoom(id, roomCommand));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
    roomService.deleteRoom(id);
    return ResponseEntity.noContent().build();
  }
}