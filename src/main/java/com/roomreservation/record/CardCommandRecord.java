package com.roomreservation.record;

public record CardCommandRecord(
  String cardNumber,
  boolean active,
  Long userId
) {}
