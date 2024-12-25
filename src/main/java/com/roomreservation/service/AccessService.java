package com.roomreservation.service;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.record.AccessRequestRecord;
import com.roomreservation.record.AccessResponseRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.CardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccessService {
  
  @Autowired
  private BookingDao bookingDao;
  
  @Autowired
  private CardDao cardDao;
  
  public AccessResponseRecord validateAccess(AccessRequestRecord requestRecord) {
    Long cardId = requestRecord.cardId();
    Long roomId = requestRecord.roomId();
    LocalDateTime now = LocalDateTime.now();
    
    // Validate card
    if (!cardDao.existsById(cardId)) {
      return new AccessResponseRecord(false);
    }
    
    // Validate booking
    BookingEntity booking = bookingDao.findValidBooking(cardId, roomId, now);
    boolean accessGranted = booking != null;
    
    return new AccessResponseRecord(accessGranted);
  }
}