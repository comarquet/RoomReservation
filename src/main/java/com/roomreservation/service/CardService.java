package com.roomreservation.service;

import com.roomreservation.mapper.CardMapper;
import com.roomreservation.model.CardEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.CardCommandRecord;
import com.roomreservation.record.CardRecord;
import com.roomreservation.repository.CardDao;
import com.roomreservation.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
  
  private final CardDao cardDao;
  private final UserDao userDao;
  
  public CardService(CardDao cardDao, UserDao userDao) {
    this.cardDao = cardDao;
    this.userDao = userDao;
  }
  
  public List<CardRecord> getAllCards() {
    return cardDao.findAll().stream()
      .map(CardMapper::of)
      .collect(Collectors.toList());
  }
  
  public CardEntity assignCardToUser(Long userId, CardCommandRecord CardCommandRecord) {
    UserEntity userEntity = userDao.findById(userId)
      .orElseThrow(() -> new RuntimeException("UserEntity not found"));
    
    if (userEntity.getCardEntity() != null) {
      throw new RuntimeException("UserEntity already has a cardEntity");
    }
    
    CardEntity cardEntity = new CardEntity();
    cardEntity.setCardNumber(CardCommandRecord.cardNumber());
    cardEntity.setActive(CardCommandRecord.active());
    cardEntity.setUserEntity(userEntity);
    
    return cardDao.save(cardEntity);
  }
  
  public void deactivateCard(Long userId) {
    UserEntity userEntity = userDao.findById(userId)
      .orElseThrow(() -> new RuntimeException("UserEntity not found"));
    
    CardEntity cardEntity = userEntity.getCardEntity();
    if (cardEntity != null) {
      cardEntity.setActive(false);
      cardDao.save(cardEntity);
    }
  }
}
