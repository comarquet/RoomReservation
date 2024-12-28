package com.roomreservation;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.model.CardEntity;
import com.roomreservation.model.RoomEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.AccessRequestRecord;
import com.roomreservation.record.AccessResponseRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.CardDao;
import com.roomreservation.service.AccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessServiceTest {
  @Mock
  private BookingDao bookingDao;
  
  @Mock
  private CardDao cardDao;
  
  @InjectMocks
  private AccessService accessService;
  
  private BookingEntity mockBooking;
  private CardEntity mockCard;
  private UserEntity mockUser;
  private RoomEntity mockRoom;
  
  @BeforeEach
  void setUp() {
    // Setup mock user
    mockUser = new UserEntity();
    mockUser.setId(1L);
    mockUser.setEmail("test@test.com");
    
    // Setup mock card
    mockCard = new CardEntity();
    mockCard.setId(1L);
    mockCard.setCardNumber("CARD-TEST001");
    mockCard.setUserEntity(mockUser);
    
    // Setup mock room
    mockRoom = new RoomEntity();
    mockRoom.setId(1L);
    mockRoom.setName("Test Room");
    
    // Setup mock booking
    mockBooking = new BookingEntity();
    mockBooking.setId(1L);
    mockBooking.setStartTime(LocalDateTime.now().minusHours(1));
    mockBooking.setEndTime(LocalDateTime.now().plusHours(1));
    mockBooking.setRoomEntity(mockRoom);
    mockBooking.setUserEntity(mockUser);
  }
  
  @Test
  void validateAccess_Success() {
    // Arrange
    Long cardId = 1L;
    Long roomId = 1L;
    AccessRequestRecord request = new AccessRequestRecord(cardId, roomId);
    
    when(cardDao.existsById(cardId)).thenReturn(true);
    when(bookingDao.findValidBooking(eq(cardId), eq(roomId), any(LocalDateTime.class)))
      .thenReturn(mockBooking);
    
    // Act
    AccessResponseRecord response = accessService.validateAccess(request);
    
    // Assert
    assertTrue(response.accessGranted());
  }
  
  @Test
  void validateAccess_InvalidCard() {
    // Arrange
    Long cardId = 999L;
    Long roomId = 1L;
    AccessRequestRecord request = new AccessRequestRecord(cardId, roomId);
    
    when(cardDao.existsById(cardId)).thenReturn(false);
    
    // Act
    AccessResponseRecord response = accessService.validateAccess(request);
    
    // Assert
    assertFalse(response.accessGranted());
  }
  
  @Test
  void validateAccess_NoValidBooking() {
    // Arrange
    Long cardId = 1L;
    Long roomId = 1L;
    AccessRequestRecord request = new AccessRequestRecord(cardId, roomId);
    
    when(cardDao.existsById(cardId)).thenReturn(true);
    when(bookingDao.findValidBooking(eq(cardId), eq(roomId), any(LocalDateTime.class)))
      .thenReturn(null);
    
    // Act
    AccessResponseRecord response = accessService.validateAccess(request);
    
    // Assert
    assertFalse(response.accessGranted());
  }
}
