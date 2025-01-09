package com.roomreservation.record;

import java.time.LocalDateTime;

/**
 * Record representing a room booking in the system.
 * Contains complete booking information including room details.
 *
 * @param id The unique identifier of the booking
 * @param startTime The start time of the booking
 * @param endTime The end time of the booking
 * @param roomId The unique identifier of the booked room
 * @param roomName The name of the booked room
 * @param userId The unique identifier of the user who made the booking
 */
public record BookingRecord(
  Long id,
  LocalDateTime startTime,
  LocalDateTime endTime,
  Long roomId,
  String roomName,
  Long userId
) {}
