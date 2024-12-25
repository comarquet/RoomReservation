package com.roomreservation.api;

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
  
  @PostMapping
  public ResponseEntity<BookingRecord> createBooking(@RequestBody BookingCommandRecord bookingCommandRecord) {
    return ResponseEntity.ok(bookingService.createBooking(bookingCommandRecord));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
    return ResponseEntity.noContent().build();
  }
}