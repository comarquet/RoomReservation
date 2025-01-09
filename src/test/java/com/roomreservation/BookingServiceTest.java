package com.roomreservation;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.model.RoomEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.BookingCommandRecord;
import com.roomreservation.record.BookingRecord;
import com.roomreservation.record.RoomRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.RoomDao;
import com.roomreservation.repository.UserDao;
import com.roomreservation.service.BookingService;
import com.roomreservation.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
  @Mock
  private BookingDao bookingDao;
  @Mock
  private RoomDao roomDao;
  @Mock
  private UserDao userDao;
  @Mock
  private RoomService roomService;
  
  @InjectMocks
  private BookingService bookingService;
  
  private BookingEntity mockBooking;
  private RoomEntity mockRoom;
  private UserEntity mockUser;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  
  @BeforeEach
  void setUp() {
    mockUser = new UserEntity();
    mockUser.setId(1L);
    mockUser.setEmail("test@test.com");
    
    mockRoom = new RoomEntity();
    mockRoom.setId(1L);
    mockRoom.setName("Test Room");
    
    startTime = LocalDateTime.now().plusHours(1);
    endTime = startTime.plusHours(2);
    
    mockBooking = new BookingEntity();
    mockBooking.setId(1L);
    mockBooking.setStartTime(startTime);
    mockBooking.setEndTime(endTime);
    mockBooking.setRoomEntity(mockRoom);
    mockBooking.setUserEntity(mockUser);
  }
  
  @Test
  void createBooking_Success() {
    BookingCommandRecord command = new BookingCommandRecord(
      startTime, endTime, mockRoom.getId(), mockUser.getId()
    );
    
    when(roomDao.findById(mockRoom.getId())).thenReturn(Optional.of(mockRoom));
    when(userDao.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
    when(roomService.getAvailableRooms(any(), any()))
      .thenReturn(List.of(new RoomRecord(mockRoom.getId(), mockRoom.getName(), List.of())));
    when(bookingDao.save(any())).thenReturn(mockBooking);
    
    BookingRecord result = bookingService.createBooking(command);
    
    assertNotNull(result);
    assertEquals(mockBooking.getId(), result.id());
    assertEquals(mockRoom.getId(), result.roomId());
    assertEquals(mockUser.getId(), result.userId());
  }
  
  @Test
  void createBooking_RoomNotAvailable() {
    BookingCommandRecord command = new BookingCommandRecord(
      startTime, endTime, mockRoom.getId(), mockUser.getId()
    );
    
    when(roomDao.findById(mockRoom.getId())).thenReturn(Optional.of(mockRoom));
    when(userDao.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
    when(roomService.getAvailableRooms(any(), any())).thenReturn(List.of());
    
    assertThrows(RuntimeException.class, () -> bookingService.createBooking(command));
  }
}