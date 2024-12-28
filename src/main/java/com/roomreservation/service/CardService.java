package com.roomreservation.service;

import com.roomreservation.mapper.CardMapper;
import com.roomreservation.model.CardEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.CardCommandRecord;
import com.roomreservation.record.CardRecord;
import com.roomreservation.repository.CardDao;
import com.roomreservation.repository.UserDao;
import jakarta.transaction.Transactional;
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
  
  @Transactional
  public CardEntity assignCardToUser(Long userId, CardCommandRecord cardCommand) {
    UserEntity userEntity = userDao.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found"));
    
    if (cardDao.findByCardNumber(cardCommand.cardNumber()).isPresent()) {
      throw new RuntimeException("Card number already exists");
    }
    
    if (userEntity.getCardEntity() != null) {
      cardDao.delete(userEntity.getCardEntity());
      userEntity.setCardEntity(null);
    }
    
    CardEntity cardEntity = new CardEntity();
    cardEntity.setCardNumber(cardCommand.cardNumber());
    cardEntity.setUserEntity(userEntity);
    userEntity.setCardEntity(cardEntity);
    
    userDao.save(userEntity);
    return cardDao.save(cardEntity);
  }
}
