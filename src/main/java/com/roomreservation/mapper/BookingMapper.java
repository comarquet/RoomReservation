package com.roomreservation.mapper;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.record.BookingRecord;

public class BookingMapper {
  public static BookingRecord of(BookingEntity bookingEntity) {
    return new BookingRecord(
      bookingEntity.getId(),
      bookingEntity.getStartTime(),
      bookingEntity.getEndTime(),
      bookingEntity.getRoomEntity().getId(),
      bookingEntity.getUserEntity().getId()
    );
  }
}
