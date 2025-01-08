package com.roomreservation.api;

import com.roomreservation.config.BookingConflictException;
import com.roomreservation.record.BookingCommandRecord;
import com.roomreservation.record.BookingRecord;
import com.roomreservation.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller handling booking operations.
 * Provides endpoints for creating, updating, and managing room bookings.
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
   * @return ResponseEntity containing list of all bookings
   */
  @GetMapping
  public ResponseEntity<List<BookingRecord>> getAllBookings() {
    return ResponseEntity.ok(bookingService.getAllBookings());
  }
  
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<BookingRecord>> getUserBookings(@PathVariable Long userId) {
    return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
  }
  
  /**
   * Creates a new booking.
   * @param bookingCommandRecord Details of the booking to create
   * @return ResponseEntity containing the created booking or error message
   */
  @PostMapping
  public ResponseEntity<?> createBooking(@RequestBody BookingCommandRecord bookingCommandRecord) {
    try {
      return ResponseEntity.ok(bookingService.createBooking(bookingCommandRecord));
    } catch (BookingConflictException e) {
      return ResponseEntity.status(409).body(e.getMessage());
    }
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
    return ResponseEntity.noContent().build();
  }
  
  /**
   * Updates an existing booking.
   * @param id ID of the booking to update
   * @param bookingCommand Updated booking details
   * @return ResponseEntity containing the updated booking or error message
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