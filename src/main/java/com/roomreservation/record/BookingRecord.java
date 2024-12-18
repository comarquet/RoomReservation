package com.roomreservation.record;

import java.time.LocalDateTime;

public record BookingRecord(
  Long id,
  LocalDateTime startTime,
  LocalDateTime endTime,
  boolean active,
  Long roomId,
  Long userId
) {}
