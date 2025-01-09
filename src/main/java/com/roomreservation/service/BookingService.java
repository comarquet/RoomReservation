package com.roomreservation.service;

import com.roomreservation.config.BookingConflictException;
import com.roomreservation.mapper.BookingMapper;
import com.roomreservation.model.BookingEntity;
import com.roomreservation.model.RoomEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.BookingCommandRecord;
import com.roomreservation.record.BookingRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.RoomDao;
import com.roomreservation.repository.UserDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service managing room booking operations in the system.
 * Handles creation, modification, and deletion of bookings while enforcing business rules
 * such as booking time constraints and room availability.
 */
@Service
public class BookingService {
  
  private final BookingDao bookingDao;
  private final RoomDao roomDao;
  private final UserDao userDao;
  private final RoomService roomService;
  
  public BookingService(BookingDao bookingDao, RoomDao roomDao, UserDao userDao, RoomService roomService) {
    this.bookingDao = bookingDao;
    this.roomDao = roomDao;
    this.userDao = userDao;
    this.roomService = roomService;
  }
  
  /**
   * Retrieves all bookings in the system.
   *
   * @return List of BookingRecord representing all bookings
   */
  public List<BookingRecord> getAllBookings() {
    return bookingDao.findAll().stream()
      .map(BookingMapper::of)
      .collect(Collectors.toList());
  }
  
  /**
   * Creates a new booking in the system.
   * Performs validation checks including:
   * - Room availability for the requested time slot
   * - Booking time constraints (not in past, maximum duration)
   * - Room existence
   * - User existence
   *
   * @param bookingCommand Contains booking details including time, room, and user
   * @return BookingRecord representing the created booking
   * @throws BookingConflictException if room is already booked for the requested time
   * @throws RuntimeException if validation fails or resources not found
   */
  @Transactional
  public BookingRecord createBooking(BookingCommandRecord bookingCommand) {
    try {
      validateBookingTime(bookingCommand.startTime(), bookingCommand.endTime());
      
      if (hasConflictingBookings(bookingCommand.roomId(), bookingCommand.startTime(),
        bookingCommand.endTime(), -1L)) {
        throw new BookingConflictException("Room is already booked for this time slot");
      }
      
      RoomEntity room = roomDao.findById(bookingCommand.roomId())
        .orElseThrow(() -> new RuntimeException("Room not found"));
      
      UserEntity user = userDao.findById(bookingCommand.userId())
        .orElseThrow(() -> new RuntimeException("User not found"));
      
      if (!roomService.getAvailableRooms(bookingCommand.startTime(), bookingCommand.endTime())
        .stream()
        .anyMatch(r -> r.id().equals(room.getId()))) {
        throw new RuntimeException("Room is not available for the selected time slot");
      }
      
      BookingEntity booking = new BookingEntity();
      booking.setStartTime(bookingCommand.startTime());
      booking.setEndTime(bookingCommand.endTime());
      booking.setRoomEntity(room);
      booking.setUserEntity(user);
      
      return BookingMapper.of(bookingDao.save(booking));
    
    } catch (BookingConflictException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Error creating booking: " + e.getMessage());
    }
  }
  
  /**
   * Validates the time constraints for a booking.
   * Enforces the following rules:
   * - Booking cannot be in the past
   * - End time must be after start time
   * - Maximum booking duration is 4 hours
   *
   * @param startTime The proposed booking start time
   * @param endTime The proposed booking end time
   * @throws RuntimeException if any of the following conditions are met:
   *         - Start time is in the past
   *         - End time is before start time
   *         - Booking duration exceeds 4 hours
   */
  private void validateBookingTime(LocalDateTime startTime, LocalDateTime endTime) {
    if (startTime.isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Cannot book in the past");
    }
    if (endTime.isBefore(startTime)) {
      throw new RuntimeException("End time must be after start time");
    }
    if (startTime.plusHours(4).isBefore(endTime)) {
      throw new RuntimeException("Maximum booking duration is 4 hours");
    }
  }
  
  /**
   * Deletes a booking from the system.
   *
   * @param id ID of the booking to delete
   */
  public void deleteBooking(Long id) {
    bookingDao.deleteById(id);
  }
  
  /**
   * Retrieves all bookings for a specific user.
   *
   * @param userId ID of the user whose bookings to retrieve
   * @return List of BookingRecord for the specified user
   */
  public List<BookingRecord> getBookingsByUserId(Long userId) {
    return bookingDao.findByUserEntityId(userId).stream()
      .map(BookingMapper::of)
      .collect(Collectors.toList());
  }
  
  /**
   * Updates an existing booking.
   * Validates the modification ensuring:
   * - Room availability for new time slot
   * - Booking time constraints
   * - User owns the booking
   *
   * @param id ID of the booking to update
   * @param bookingCommand New booking details
   * @return BookingRecord representing the updated booking
   * @throws BookingConflictException if room is already booked for the requested time
   * @throws RuntimeException if validation fails or booking not found
   */
  @Transactional
  public BookingRecord updateBooking(Long id, BookingCommandRecord bookingCommand) {
    try {
      validateBookingTime(bookingCommand.startTime(), bookingCommand.endTime());
      
      if (hasConflictingBookings(bookingCommand.roomId(), bookingCommand.startTime(),
        bookingCommand.endTime(), id)) {
        throw new BookingConflictException("Room is already booked for this time slot");
      }
      
      BookingEntity booking = bookingDao.findById(id)
        .orElseThrow(() -> new RuntimeException("Booking not found"));
      
      validateBookingTime(bookingCommand.startTime(), bookingCommand.endTime());
      
      RoomEntity room = roomDao.findById(bookingCommand.roomId())
        .orElseThrow(() -> new RuntimeException("Room not found"));
      
      if (!booking.getUserEntity().getId().equals(bookingCommand.userId())) {
        throw new RuntimeException("Cannot modify booking owned by another user");
      }
      
      if (!room.getId().equals(booking.getRoomEntity().getId()) &&
        !roomService.getAvailableRooms(bookingCommand.startTime(), bookingCommand.endTime())
          .stream()
          .anyMatch(r -> r.id().equals(room.getId()))) {
        throw new RuntimeException("Room is not available for the selected time slot");
      }
      
      booking.setStartTime(bookingCommand.startTime());
      booking.setEndTime(bookingCommand.endTime());
      booking.setRoomEntity(room);
      
      return BookingMapper.of(bookingDao.save(booking));
      
    } catch (BookingConflictException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Error updating booking: " + e.getMessage());
    }
  }
  
  /**
   * Checks for any conflicting bookings for a room during a specified time period.
   * A conflict occurs when there is an overlap between the requested time slot
   * and any existing booking for the room.
   *
   * @param roomId The ID of the room to check
   * @param startTime Start time of the period to check
   * @param endTime End time of the period to check
   * @param excludeBookingId Optional booking ID to exclude from the check (used during updates)
   * @return true if there are any conflicting bookings, false otherwise
   */
  private boolean hasConflictingBookings(Long roomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId) {
    return bookingDao.findByRoomEntityId(roomId).stream()
      .filter(booking -> !booking.getId().equals(excludeBookingId))
      .anyMatch(booking ->
        (startTime.isBefore(booking.getEndTime()) || startTime.isEqual(booking.getEndTime())) &&
          (endTime.isAfter(booking.getStartTime()) || endTime.isEqual(booking.getStartTime()))
      );
  }
}
