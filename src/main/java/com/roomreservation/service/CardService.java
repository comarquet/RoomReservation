package com.roomreservation.service;

import com.roomreservation.record.CardRecord;
import com.roomreservation.model.CardEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.repository.CardDao;
import com.roomreservation.repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class CardService {
  
  private final CardDao cardDao;
  private final UserDao userDao;
  
  public CardService(CardDao cardDao, UserDao userDao) {
    this.cardDao = cardDao;
    this.userDao = userDao;
  }
  
  public CardEntity assignCardToUser(Long userId, CardRecord CardRecord) {
    UserEntity userEntity = userDao.findById(userId)
      .orElseThrow(() -> new RuntimeException("UserEntity not found"));
    
    if (userEntity.getCardEntity() != null) {
      throw new RuntimeException("UserEntity already has a cardEntity");
    }
    
    CardEntity cardEntity = new CardEntity();
    cardEntity.setCardNumber(CardRecord.cardNumber());
    cardEntity.setActive(CardRecord.active());
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
