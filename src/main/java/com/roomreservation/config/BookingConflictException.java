package com.roomreservation.config;

/**
 * Custom exception class for handling booking conflicts in the room reservation system.
 * This exception is thrown when attempting to create or modify a booking that overlaps
 * with an existing booking for the same room.
 *
 * <p>Usage examples:
 * - When creating a new booking that conflicts with existing bookings
 * - When updating a booking to a time slot that is already taken
 * - When multiple users attempt to book the same room for overlapping time periods
 *
 * @see com.roomreservation.service.BookingService
 */
public class BookingConflictException extends RuntimeException {
  /**
   * Constructs a new BookingConflictException with the specified error message.
   *
   * @param message The detail message explaining the nature of the conflict
   */
  public BookingConflictException(String message) {
    super(message);
  }
}
