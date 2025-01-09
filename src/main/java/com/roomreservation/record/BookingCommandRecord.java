package com.roomreservation.record;

import java.time.LocalDateTime;

/**
 * Record representing a command to create or update a booking.
 * Contains all necessary information for scheduling a room reservation.
 *
 * @param startTime The desired start time of the booking
 * @param endTime The desired end time of the booking
 * @param roomId The unique identifier of the room to be booked
 * @param userId The unique identifier of the user making the booking
 */
public record BookingCommandRecord(
  LocalDateTime startTime,
  LocalDateTime endTime,
  Long roomId,
  Long userId
) {}
