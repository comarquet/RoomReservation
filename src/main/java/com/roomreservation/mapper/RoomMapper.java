package com.roomreservation.mapper;

import com.roomreservation.model.RoomEntity;
import com.roomreservation.record.RoomRecord;

/**
 * Utility class for mapping between RoomEntity objects and their DTO representations.
 * Provides static conversion methods to transform room entities into RoomRecord objects.
 *
 * @see com.roomreservation.model.RoomEntity
 * @see com.roomreservation.record.RoomRecord
 */
public class RoomMapper {
  /**
   * Converts a RoomEntity to its DTO representation.
   * Maps the room's basic information and associated bookings.
   *
   * @param roomEntity The room entity to convert, must not be null
   * @return RoomRecord containing the room's data in DTO format
   * @throws NullPointerException if roomEntity is null
   */
  public static RoomRecord of(RoomEntity roomEntity) {
    return new RoomRecord(
      roomEntity.getId(),
      roomEntity.getName(),
      roomEntity.getCapacity(),
      roomEntity.getBookingEntities()
    );
  }
}