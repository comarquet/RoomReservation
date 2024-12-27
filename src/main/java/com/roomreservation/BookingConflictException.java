package com.roomreservation;

public class BookingConflictException extends RuntimeException {
  public BookingConflictException(String message) {
    super(message);
  }
}
