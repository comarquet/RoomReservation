package com.roomreservation.repository;

import com.roomreservation.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDao extends JpaRepository<BookingEntity, Long> {
  List<BookingEntity> findByRoomEntityId(Long roomId);
  
  List<BookingEntity> findByUserEntityId(Long userId);
  
  @Query("SELECT b FROM BookingEntity b WHERE b.userEntity.cardEntity.cardNumber = :cardId AND b.roomEntity.id = :roomId AND b.startTime <= :now AND b.endTime >= :now")
  BookingEntity findValidBooking(Long cardId, Long roomId, LocalDateTime now);
}
