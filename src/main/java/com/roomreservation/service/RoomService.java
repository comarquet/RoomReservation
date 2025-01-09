package com.roomreservation.service;

import com.roomreservation.mapper.RoomMapper;
import com.roomreservation.model.RoomEntity;
import com.roomreservation.record.BookingRecord;
import com.roomreservation.record.RoomRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.RoomDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service managing room-related operations in the system.
 * Handles room creation, updates, deletions, and availability checks.
 * Implements business logic for room management and booking validation.
 */
@Service
public class RoomService {
  
  private final RoomDao roomDao;
  private final BookingDao bookingDao;
  
  public RoomService(RoomDao roomDao, BookingDao bookingDao) {
    this.roomDao = roomDao;
    this.bookingDao = bookingDao;
  }
  
  /**
   * Retrieves all rooms in the system.
   *
   * @return List of RoomRecord representing all rooms
   */
  public List<RoomRecord> getAllRooms() {
    return roomDao.findAll().stream()
      .map(RoomMapper::of)
      .collect(Collectors.toList());
  }
  
  /**
   * Retrieves a specific room by its ID.
   *
   * @param id The ID of the room to retrieve
   * @return RoomRecord representing the requested room
   * @throws RuntimeException if room is not found
   */
  public RoomRecord getRoomById(Long id) {
    RoomEntity roomEntity = roomDao.findById(id)
      .orElseThrow(() -> new RuntimeException("RoomEntity not found"));
    return RoomMapper.of(roomEntity);
  }
  
  /**
   * Creates a new room in the system.
   *
   * @param roomRecord Contains room details including name and capacity
   * @return RoomRecord representing the created room
   */
  public RoomRecord createRoom(RoomRecord roomRecord) {
    RoomEntity roomEntity = new RoomEntity();
    roomEntity.setName(roomRecord.name());
    RoomEntity savedRoomEntity = roomDao.save(roomEntity);
    return RoomMapper.of(savedRoomEntity);
  }
  
  /**
   * Updates an existing room's information.
   *
   * @param id ID of the room to update
   * @param roomRecord New room details
   * @return RoomRecord representing the updated room
   * @throws RuntimeException if room not found
   */
  public RoomRecord updateRoom(Long id, RoomRecord roomRecord) {
    RoomEntity roomEntity = roomDao.findById(id)
      .orElseThrow(() -> new RuntimeException("RoomEntity not found"));
    roomEntity.setName(roomRecord.name());
    RoomEntity updatedRoomEntity = roomDao.save(roomEntity);
    return RoomMapper.of(updatedRoomEntity);
  }
  
  /**
   * Deletes a room from the system.
   *
   * @param id ID of the room to delete
   */
  public void deleteRoom(Long id) {
    roomDao.deleteById(id);
  }
  
  /**
   * Checks room availability for a specified time period.
   * A room is considered available if it has no overlapping bookings
   * during the specified time period.
   *
   * @param startTime Start of the time period to check
   * @param endTime End of the time period to check
   * @return List of RoomRecord representing available rooms
   * @throws IllegalArgumentException if startTime is after endTime
   */
  public List<RoomRecord> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
    List<RoomEntity> allRooms = roomDao.findAll();
    return allRooms.stream()
      .filter(room -> isRoomAvailable(room, startTime, endTime))
      .map(RoomMapper::of)
      .collect(Collectors.toList());
  }
  
  /**
   * Checks if a specific room is available during a given time period.
   * A room is considered available if it has no bookings that overlap
   * with the specified time period.
   *
   * @param room The room entity to check
   * @param startTime Start of the time period to check
   * @param endTime End of the time period to check
   * @return true if the room is available, false if there are any overlapping bookings
   */
  private boolean isRoomAvailable(RoomEntity room, LocalDateTime startTime, LocalDateTime endTime) {
    List<BookingRecord> bookings = room.getBookingEntities();
    return bookings.stream()
      .noneMatch(booking ->
        (startTime.isBefore(booking.endTime()) || startTime.isEqual(booking.endTime())) &&
          (endTime.isAfter(booking.startTime()) || endTime.isEqual(booking.startTime()))
      );
  }
}

