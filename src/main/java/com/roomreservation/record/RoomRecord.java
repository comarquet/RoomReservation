package com.roomreservation.record;

import java.util.List;

/**
 * Record representing a room in the system.
 * Contains complete room information including current bookings.
 *
 * @param id The unique identifier of the room
 * @param name The display name of the room
 * @param capacity The maximum number of people the room can accommodate
 * @param bookings List of current bookings for this room
 */
public record RoomRecord(
  Long id,
  String name,
  int capacity,
  List<BookingRecord> bookings
) {}
