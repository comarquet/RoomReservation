package com.roomreservation.api;

import com.roomreservation.BookingConflictException;
import com.roomreservation.record.BookingCommandRecord;
import com.roomreservation.record.BookingRecord;
import com.roomreservation.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
  
  private final BookingService bookingService;
  
  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }
  
  @GetMapping
  public ResponseEntity<List<BookingRecord>> getAllBookings() {
    return ResponseEntity.ok(bookingService.getAllBookings());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<BookingRecord> getBookingById(@PathVariable Long id) {
    return ResponseEntity.ok(bookingService.getBookingById(id));
  }
  
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<BookingRecord>> getUserBookings(@PathVariable Long userId) {
    return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
  }
  
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
  
  @PutMapping("/{id}")
  public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingCommandRecord bookingCommand) {
    try {
      return ResponseEntity.ok(bookingService.updateBooking(id, bookingCommand));
    } catch (BookingConflictException e) {
      return ResponseEntity.status(409).body(e.getMessage());
    }
  }
}