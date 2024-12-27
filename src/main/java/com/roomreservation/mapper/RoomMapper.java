package com.roomreservation.mapper;

import com.roomreservation.model.RoomEntity;
import com.roomreservation.record.RoomRecord;

public class RoomMapper {
  public static RoomRecord of(RoomEntity roomEntity) {
    return new RoomRecord(
      roomEntity.getId(),
      roomEntity.getName(),
      roomEntity.getCapacity(),
      roomEntity.getBookingEntities()
    );
  }
}