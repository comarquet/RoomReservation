package com.roomreservation.service;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.model.CardEntity;
import com.roomreservation.record.AccessRequestRecord;
import com.roomreservation.record.AccessResponseRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.CardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccessService {
  
  @Autowired
  private BookingDao bookingDao;
  
  @Autowired
  private CardDao cardDao;
  
  public AccessResponseRecord validateAccess(AccessRequestRecord requestRecord) {
    String cardNumber = requestRecord.cardNumber();
    Long roomId = requestRecord.roomId();
    LocalDateTime now = LocalDateTime.now();
    
    // Validate card exists and is valid
    Optional<CardEntity> cardEntity = cardDao.findByCardNumber(cardNumber);
    if (cardEntity.isEmpty()) {
      return new AccessResponseRecord(false);
    }
    
    // Validate booking
    BookingEntity booking = bookingDao.findValidBooking(cardNumber, roomId, now);
    boolean accessGranted = booking != null;
    
    return new AccessResponseRecord(accessGranted);
  }
}