package com.roomreservation.repository;

import com.roomreservation.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing booking entities.
 * Provides CRUD operations and custom queries for room bookings.
 * Extends JpaRepository to inherit standard database operations.
 *
 * @see com.roomreservation.model.BookingEntity
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface BookingDao extends JpaRepository<BookingEntity, Long> {
  /**
   * Retrieves all bookings for a specific room.
   *
   * @param roomId the ID of the room to find bookings for
   * @return List of BookingEntity objects associated with the specified room
   */
  List<BookingEntity> findByRoomEntityId(Long roomId);
  
  /**
   * Retrieves all bookings for a specific user.
   *
   * @param userId the ID of the user to find bookings for
   * @return List of BookingEntity objects associated with the specified user
   */
  List<BookingEntity> findByUserEntityId(Long userId);
  
  /**
   * Finds a valid booking for the given card and room at the specified time.
   * A booking is considered valid if it encompasses the current time (now).
   *
   * @param cardNumber the access card number to validate
   * @param roomId the ID of the room to check
   * @param now the current timestamp to validate against
   * @return BookingEntity if a valid booking exists, null otherwise
   */
  @Query("SELECT b FROM BookingEntity b " +
    "WHERE b.userEntity.cardEntity.cardNumber = :cardNumber " +
    "AND b.roomEntity.id = :roomId " +
    "AND b.startTime <= :now " +
    "AND b.endTime >= :now")
  BookingEntity findValidBooking(String cardNumber, Long roomId, LocalDateTime now);
}
