package com.roomreservation.service;

import com.roomreservation.BookingConflictException;
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
  
  public List<BookingRecord> getAllBookings() {
    return bookingDao.findAll().stream()
      .map(BookingMapper::of)
      .collect(Collectors.toList());
  }
  
  public BookingRecord getBookingById(Long id) {
    BookingEntity bookingEntity = bookingDao.findById(id)
      .orElseThrow(() -> new RuntimeException("BookingEntity not found"));
    return BookingMapper.of(bookingEntity);
  }
  
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
  
  public void deleteBooking(Long id) {
    bookingDao.deleteById(id);
  }
  
  public List<BookingRecord> getBookingsByUserId(Long userId) {
    return bookingDao.findByUserEntityId(userId).stream()
      .map(BookingMapper::of)
      .collect(Collectors.toList());
  }
  
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
  
  private boolean hasConflictingBookings(Long roomId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId) {
    return bookingDao.findByRoomEntityId(roomId).stream()
      .filter(booking -> !booking.getId().equals(excludeBookingId))
      .anyMatch(booking ->
        (startTime.isBefore(booking.getEndTime()) || startTime.isEqual(booking.getEndTime())) &&
          (endTime.isAfter(booking.getStartTime()) || endTime.isEqual(booking.getStartTime()))
      );
  }
}
