package com.roomreservation.record;

import java.time.LocalDateTime;

public record BookingRecord(
  Long id,
  LocalDateTime startTime,
  LocalDateTime endTime,
  Long roomId,
  String roomName,
  Long userId
) {}
