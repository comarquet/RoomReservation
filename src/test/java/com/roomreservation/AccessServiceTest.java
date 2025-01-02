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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
  private static final String VALID_CARD_NUMBER = "CARD-TEST001";
  
  @BeforeEach
  void setUp() {
    // Setup mock user
    mockUser = new UserEntity();
    mockUser.setId(1L);
    mockUser.setEmail("test@test.com");
    
    // Setup mock card
    mockCard = new CardEntity();
    mockCard.setId(1L);
    mockCard.setCardNumber(VALID_CARD_NUMBER);
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
    Long roomId = 1L;
    AccessRequestRecord request = new AccessRequestRecord(VALID_CARD_NUMBER, roomId);
    
    when(cardDao.findByCardNumber(VALID_CARD_NUMBER)).thenReturn(Optional.of(mockCard));
    when(bookingDao.findValidBooking(eq(VALID_CARD_NUMBER), eq(roomId), any(LocalDateTime.class)))
      .thenReturn(mockBooking);
    
    // Act
    AccessResponseRecord response = accessService.validateAccess(request);
    
    // Assert
    assertTrue(response.accessGranted());
  }
  
  @Test
  void validateAccess_InvalidCard() {
    // Arrange
    String invalidCardNumber = "INVALID-CARD";
    Long roomId = 1L;
    AccessRequestRecord request = new AccessRequestRecord(invalidCardNumber, roomId);
    
    when(cardDao.findByCardNumber(invalidCardNumber)).thenReturn(Optional.empty());
    
    // Act
    AccessResponseRecord response = accessService.validateAccess(request);
    
    // Assert
    assertFalse(response.accessGranted());
  }
  
  @Test
  void validateAccess_NoValidBooking() {
    // Arrange
    Long roomId = 1L;
    AccessRequestRecord request = new AccessRequestRecord(VALID_CARD_NUMBER, roomId);
    
    when(cardDao.findByCardNumber(VALID_CARD_NUMBER)).thenReturn(Optional.of(mockCard));
    when(bookingDao.findValidBooking(eq(VALID_CARD_NUMBER), eq(roomId), any(LocalDateTime.class)))
      .thenReturn(null);
    
    // Act
    AccessResponseRecord response = accessService.validateAccess(request);
    
    // Assert
    assertFalse(response.accessGranted());
  }
  
  @Test
  void validateAccess_ValidCardButNoBooking() {
    // Arrange
    Long roomId = 1L;
    AccessRequestRecord request = new AccessRequestRecord(VALID_CARD_NUMBER, roomId);
    
    when(cardDao.findByCardNumber(VALID_CARD_NUMBER)).thenReturn(Optional.of(mockCard));
    when(bookingDao.findValidBooking(eq(VALID_CARD_NUMBER), eq(roomId), any(LocalDateTime.class)))
      .thenReturn(null);
    
    // Act
    AccessResponseRecord response = accessService.validateAccess(request);
    
    // Assert
    assertFalse(response.accessGranted());
  }
}