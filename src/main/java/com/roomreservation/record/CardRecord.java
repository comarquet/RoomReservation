package com.roomreservation.record;

public record CardRecord(
  Long id,
  String cardNumber,
  boolean active,
  Long userId
) {}
