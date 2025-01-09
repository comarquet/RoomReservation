package com.roomreservation.record;

import java.util.List;

/**
 * Record representing a room in the system.
 * Contains complete room information including current bookings.
 *
 * @param id The unique identifier of the room
 * @param name The display name of the room
 * @param bookings List of current bookings for this room
 */
public record RoomRecord(
  Long id,
  String name,
  List<BookingRecord> bookings
) {}
