package com.roomreservation.mapper;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.record.BookingRecord;

/**
 * Utility class for mapping between BookingEntity objects and their DTO representations.
 * Provides static conversion methods to transform booking entities into BookingRecord objects.
 *
 * @see com.roomreservation.model.BookingEntity
 * @see com.roomreservation.record.BookingRecord
 */
public class BookingMapper {
  /**
   * Converts a BookingEntity to its DTO representation.
   * Maps all relevant booking information including associated room and user details.
   *
   * @param bookingEntity The booking entity to convert, must not be null
   * @return BookingRecord containing the booking's data in DTO format
   * @throws NullPointerException if bookingEntity is null
   */
  public static BookingRecord of(BookingEntity bookingEntity) {
    return new BookingRecord(
      bookingEntity.getId(),
      bookingEntity.getStartTime(),
      bookingEntity.getEndTime(),
      bookingEntity.getRoomEntity().getId(),
      bookingEntity.getRoomEntity().getName(),
      bookingEntity.getUserEntity().getId()
    );
  }
}
