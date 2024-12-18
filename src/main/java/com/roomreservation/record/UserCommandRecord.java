package com.roomreservation.record;

public record UserCommandRecord(
  String firstName,
  String lastName,
  String email,
  String password,
  String role,
  Long cardId
) {}
