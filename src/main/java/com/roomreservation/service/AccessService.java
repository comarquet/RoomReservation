package com.roomreservation.service;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.record.AccessRequestRecord;
import com.roomreservation.record.AccessResponseRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.CardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service responsible for validating access control in the room reservation system.
 * Handles verification of card access to rooms based on current bookings and permissions.
 */
@Service
public class AccessService {
  
  @Autowired
  private BookingDao bookingDao;
  
  @Autowired
  private CardDao cardDao;
  
  /**
   * Validates whether a card has access to a specific room at the current time.
   * Access is granted if:
   * 1. The card exists in the system
   * 2. There is a valid booking for the card holder at the current time
   *
   * @param requestRecord Contains the card number and room ID to validate
   * @return AccessResponseRecord indicating whether access is granted
   * @see AccessRequestRecord
   * @see AccessResponseRecord
   */
  public AccessResponseRecord validateAccess(AccessRequestRecord requestRecord) {
    String cardNumber = requestRecord.cardNumber();
    Long roomId = requestRecord.roomId();
    LocalDateTime now = LocalDateTime.now();
    
    if (!cardDao.findByCardNumber(cardNumber).isPresent()) {
      return new AccessResponseRecord(false);
    }
    
    BookingEntity booking = bookingDao.findValidBooking(cardNumber, roomId, now);
    
    return new AccessResponseRecord(booking != null);
  }
}