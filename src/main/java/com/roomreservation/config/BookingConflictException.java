package com.roomreservation.config;

public class BookingConflictException extends RuntimeException {
  public BookingConflictException(String message) {
    super(message);
  }
}
