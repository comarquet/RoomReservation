package com.roomreservation.service;

import com.roomreservation.model.BookingEntity;
import com.roomreservation.model.RoomEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.RoomRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.RoomDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
  @Mock
  private RoomDao roomDao;
  
  @Mock
  private BookingDao bookingDao;
  
  @InjectMocks
  private RoomService roomService;
  
  private RoomEntity mockRoom;
  private UserEntity mockUser;
  private List<BookingEntity> mockBookings;
  
  @BeforeEach
  void setUp() {
    // Setup mock user
    mockUser = new UserEntity();
    mockUser.setId(1L);
    mockUser.setFirstName("Test");
    mockUser.setLastName("User");
    mockUser.setEmail("test@test.com");
    
    // Setup mock room
    mockRoom = new RoomEntity();
    mockRoom.setId(1L);
    mockRoom.setName("Test Room");
    mockRoom.setCapacity(10);
    
    // Setup mock bookings list
    mockBookings = new ArrayList<>();
    mockRoom.setBookingEntities(mockBookings);
  }
  
  @Test
  void createRoom_Success() {
    // Arrange
    RoomRecord roomRecord = new RoomRecord(null, "Test Room", 10, List.of());
    when(roomDao.save(any(RoomEntity.class))).thenReturn(mockRoom);
    
    // Act
    RoomRecord result = roomService.createRoom(roomRecord);
    
    // Assert
    assertNotNull(result);
    assertEquals(mockRoom.getName(), result.name());
    assertEquals(mockRoom.getCapacity(), result.capacity());
  }
  
  @Test
  void getRoomById_Success() {
    // Arrange
    when(roomDao.findById(1L)).thenReturn(Optional.of(mockRoom));
    
    // Act
    RoomRecord result = roomService.getRoomById(1L);
    
    // Assert
    assertNotNull(result);
    assertEquals(mockRoom.getId(), result.id());
    assertEquals(mockRoom.getName(), result.name());
  }
  
  @Test
  void getRoomById_NotFound() {
    // Arrange
    when(roomDao.findById(999L)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(RuntimeException.class, () -> roomService.getRoomById(999L));
  }
  
  @Test
  void getAvailableRooms_Success() {
    // Arrange
    LocalDateTime startTime = LocalDateTime.now().plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);
    
    when(roomDao.findAll()).thenReturn(List.of(mockRoom));
    
    // Act
    List<RoomRecord> result = roomService.getAvailableRooms(startTime, endTime);
    
    // Assert
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(mockRoom.getId(), result.get(0).id());
  }
  
  @Test
  void getAvailableRooms_WithConflictingBooking() {
    // Arrange
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime startTime = now.plusHours(1);
    LocalDateTime endTime = startTime.plusHours(2);
    
    // Create a conflicting booking with proper user association
    BookingEntity conflictingBooking = new BookingEntity();
    conflictingBooking.setId(1L);
    conflictingBooking.setStartTime(startTime.minusMinutes(30));
    conflictingBooking.setEndTime(endTime.plusMinutes(30));
    conflictingBooking.setRoomEntity(mockRoom);
    conflictingBooking.setUserEntity(mockUser); // Set the user entity
    mockBookings.add(conflictingBooking);
    
    when(roomDao.findAll()).thenReturn(List.of(mockRoom));
    
    // Act
    List<RoomRecord> result = roomService.getAvailableRooms(startTime, endTime);
    
    // Assert
    assertTrue(result.isEmpty());
  }
  
  @Test
  void updateRoom_Success() {
    // Arrange
    Long roomId = 1L;
    RoomRecord updateRecord = new RoomRecord(roomId, "Updated Room", 15, List.of());
    when(roomDao.findById(roomId)).thenReturn(Optional.of(mockRoom));
    when(roomDao.save(any(RoomEntity.class))).thenReturn(mockRoom);
    
    // Act
    RoomRecord result = roomService.updateRoom(roomId, updateRecord);
    
    // Assert
    assertNotNull(result);
    assertEquals(updateRecord.name(), result.name());
    assertEquals(updateRecord.capacity(), result.capacity());
  }
  
  @Test
  void getAllRooms_Success() {
    // Arrange
    List<RoomEntity> mockRooms = List.of(mockRoom);
    when(roomDao.findAll()).thenReturn(mockRooms);
    
    // Act
    List<RoomRecord> results = roomService.getAllRooms();
    
    // Assert
    assertFalse(results.isEmpty());
    assertEquals(1, results.size());
    assertEquals(mockRoom.getId(), results.get(0).id());
    assertEquals(mockRoom.getName(), results.get(0).name());
  }
}