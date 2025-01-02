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
    String cardNumber = requestRecord.cardNumber();
    Long roomId = requestRecord.roomId();
    LocalDateTime now = LocalDateTime.now();
    
    System.out.println("Debug - Card Number: " + cardNumber);
    System.out.println("Debug - Room ID: " + roomId);
    System.out.println("Debug - Current Time: " + now);
    
    // Validate card exists
    if (!cardDao.findByCardNumber(cardNumber).isPresent()) {
      System.out.println("Debug - Card not found");
      return new AccessResponseRecord(false);
    }
    
    // Validate booking
    BookingEntity booking = bookingDao.findValidBooking(cardNumber, roomId, now);
    System.out.println("Debug - Booking found: " + (booking != null));
    
    return new AccessResponseRecord(booking != null);
  }
}