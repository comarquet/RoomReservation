package com.roomreservation.service;

import com.roomreservation.mapper.CardMapper;
import com.roomreservation.record.CardRecord;
import com.roomreservation.repository.CardDao;
import com.roomreservation.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
  
  private final CardDao cardDao;
  
  public CardService(CardDao cardDao, UserDao userDao) {
    this.cardDao = cardDao;
  }
  
  public List<CardRecord> getAllCards() {
    return cardDao.findAll().stream()
      .map(CardMapper::of)
      .collect(Collectors.toList());
  }
}
