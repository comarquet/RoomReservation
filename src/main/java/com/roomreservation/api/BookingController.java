package com.roomreservation.api;

import com.roomreservation.config.BookingConflictException;
import com.roomreservation.record.BookingCommandRecord;
import com.roomreservation.record.BookingRecord;
import com.roomreservation.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing room bookings.
 * Provides endpoints for creating, retrieving, updating, and deleting room bookings.
 * Handles booking conflicts and validation of booking requests.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
  
  private final BookingService bookingService;
  
  /**
   * Constructs a BookingController with required dependencies.
   * @param bookingService Service for handling booking operations
   */
  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }
  
  /**
   * Retrieves all bookings in the system.
   *
   * @return ResponseEntity containing list of all bookings
   * @see BookingRecord
   */
  @GetMapping
  public ResponseEntity<List<BookingRecord>> getAllBookings() {
    return ResponseEntity.ok(bookingService.getAllBookings());
  }
  
  /**
   * Retrieves all bookings for a specific user.
   *
   * @param userId The ID of the user whose bookings to retrieve
   * @return ResponseEntity containing list of user's bookings
   * @see BookingRecord
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<BookingRecord>> getUserBookings(@PathVariable Long userId) {
    return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
  }
  
  /**
   * Creates a new booking.
   *
   * @param bookingCommandRecord Details of the booking to create
   * @return ResponseEntity containing the created booking or error message
   * @throws BookingConflictException if the requested time slot conflicts with existing bookings
   * @see BookingCommandRecord
   * @see BookingRecord
   */
  @PostMapping
  public ResponseEntity<?> createBooking(@RequestBody BookingCommandRecord bookingCommandRecord) {
    try {
      return ResponseEntity.ok(bookingService.createBooking(bookingCommandRecord));
    } catch (BookingConflictException e) {
      return ResponseEntity.status(409).body(e.getMessage());
    }
  }
  
  /**
   * Deletes a booking.
   *
   * @param id The ID of the booking to delete
   * @return ResponseEntity with no content if deletion successful
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
    return ResponseEntity.noContent().build();
  }
  
  /**
   * Updates an existing booking.
   *
   * @param id The ID of the booking to update
   * @param bookingCommand Updated booking details
   * @return ResponseEntity containing the updated booking or error message
   * @throws BookingConflictException if the requested time slot conflicts with existing bookings
   * @throws RuntimeException if booking not found or user not authorized
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingCommandRecord bookingCommand) {
    try {
      return ResponseEntity.ok(bookingService.updateBooking(id, bookingCommand));
    } catch (BookingConflictException e) {
      return ResponseEntity.status(409).body(e.getMessage());
    }
  }
}