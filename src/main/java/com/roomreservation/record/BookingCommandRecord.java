package com.roomreservation.record;

import java.time.LocalDateTime;

public record BookingCommandRecord(
  LocalDateTime startTime,
  LocalDateTime endTime,
  Long roomId,
  Long userId
) {}
