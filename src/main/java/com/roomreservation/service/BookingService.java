package com.roomreservation.service;

import com.roomreservation.mapper.BookingMapper;
import com.roomreservation.model.BookingEntity;
import com.roomreservation.model.RoomEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.BookingCommandRecord;
import com.roomreservation.record.BookingRecord;
import com.roomreservation.repository.BookingDao;
import com.roomreservation.repository.RoomDao;
import com.roomreservation.repository.UserDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
  
  private final BookingDao bookingDao;
  private final RoomDao roomDao;
  private final UserDao userDao;
  
  public BookingService(BookingDao bookingDao, RoomDao roomDao, UserDao userDao) {
    this.bookingDao = bookingDao;
    this.roomDao = roomDao;
    this.userDao = userDao;
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
  
  public BookingRecord createBooking(BookingCommandRecord bookingCommandRecord) {
    RoomEntity roomEntity = roomDao.findById(bookingCommandRecord.roomId())
      .orElseThrow(() -> new RuntimeException("RoomEntity not found"));
    UserEntity userEntity = userDao.findById(bookingCommandRecord.userId())
      .orElseThrow(() -> new RuntimeException("UserEntity not found"));
    
    BookingEntity bookingEntity = new BookingEntity();
    bookingEntity.setRoomEntity(roomEntity);
    bookingEntity.setUserEntity(userEntity);
    bookingEntity.setStartTime(bookingCommandRecord.startTime());
    bookingEntity.setEndTime(bookingCommandRecord.endTime());
    
    BookingEntity savedBookingEntity = bookingDao.save(bookingEntity);
    return BookingMapper.of(savedBookingEntity);
  }
  
  public void deleteBooking(Long id) {
    bookingDao.deleteById(id);
  }
}
