package com.roomreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a room booking in the system.
 * Maps to the SP_BOOKING table in the database and manages the relationship
 * between users and rooms for specific time periods.
 *
 * @see RoomEntity
 * @see UserEntity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_BOOKING")
public class BookingEntity {
  
  /**
   * Unique identifier for the booking.
   * Auto-generated using database sequence.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  /**
   * Start time of the booking.
   * Must be in the future when booking is created.
   * Used to determine the beginning of the reservation period.
   */
  @Column(nullable = false)
  private LocalDateTime startTime;
  
  /**
   * End time of the booking.
   * Must be after the start time.
   * Used to determine the end of the reservation period.
   */
  @Column(nullable = false)
  private LocalDateTime endTime;
  
  /**
   * The room that is booked.
   * Many bookings can reference one room (Many-to-One relationship).
   * Cannot be null as every booking must be associated with a room.
   */
  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private RoomEntity roomEntity;
  
  /**
   * The user who made the booking.
   * Many bookings can be made by one user (Many-to-One relationship).
   * Cannot be null as every booking must be associated with a user.
   */
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity userEntity;
}

