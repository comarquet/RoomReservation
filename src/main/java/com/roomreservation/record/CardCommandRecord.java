package com.roomreservation.record;

public record CardCommandRecord(
  String cardNumber,
  Long userId
) {}
