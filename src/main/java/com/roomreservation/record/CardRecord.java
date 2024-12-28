package com.roomreservation.record;

public record CardRecord(
  Long id,
  String cardNumber,
  Long userId
) {}
