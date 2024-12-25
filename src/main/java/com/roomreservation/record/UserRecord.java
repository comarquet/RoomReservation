package com.roomreservation.record;

public record UserRecord(
  Long id,
  String firstName,
  String lastName,
  String email,
  String password,
  Long cardId
) {}
