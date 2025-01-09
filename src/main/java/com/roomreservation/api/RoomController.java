package com.roomreservation.api;

import com.roomreservation.record.RoomRecord;
import com.roomreservation.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing rooms.
 * Provides endpoints for creating, retrieving, updating, and deleting rooms.
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {
  
  private final RoomService roomService;
  
  public RoomController(RoomService roomService) {
    this.roomService = roomService;
  }
  
  /**
   * Retrieves all rooms in the system.
   *
   * @return ResponseEntity containing list of all rooms
   * @see RoomRecord
   */
  @GetMapping
  public ResponseEntity<List<RoomRecord>> getAllRooms() {
    return ResponseEntity.ok(roomService.getAllRooms());
  }
  
  /**
   * Retrieves a specific room by its ID.
   *
   * @param id The ID of the room to retrieve
   * @return ResponseEntity containing the requested room
   * @throws RuntimeException if room not found
   * @see RoomRecord
   */
  @GetMapping("/{id}")
  public ResponseEntity<RoomRecord> getRoomById(@PathVariable Long id) {
    return ResponseEntity.ok(roomService.getRoomById(id));
  }
  
  /**
   * Creates a new room.
   *
   * @param roomCommand Room details to create
   * @return ResponseEntity containing the created room
   * @see RoomRecord
   */
  @PostMapping
  public ResponseEntity<RoomRecord> createRoom(@RequestBody RoomRecord roomCommand) {
    return ResponseEntity.ok(roomService.createRoom(roomCommand));
  }
  
  /**
   * Updates an existing room.
   *
   * @param id The ID of the room to update
   * @param roomCommand Updated room details
   * @return ResponseEntity containing the updated room
   * @throws RuntimeException if room not found
   */
  @PutMapping("/{id}")
  public ResponseEntity<RoomRecord> updateRoom(@PathVariable Long id, @RequestBody RoomRecord roomCommand) {
    return ResponseEntity.ok(roomService.updateRoom(id, roomCommand));
  }
  
  /**
   * Deletes a room.
   *
   * @param id The ID of the room to delete
   * @return ResponseEntity with no content if deletion successful
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
    roomService.deleteRoom(id);
    return ResponseEntity.noContent().build();
  }
}