package com.roomreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a room booking in the system.
 * A booking associates a user with a room for a specific time period.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_BOOKING")
public class BookingEntity {
  
  /**
   * Unique identifier for the booking
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  /**
   * Start time of the booking
   */
  @Column(nullable = false)
  private LocalDateTime startTime;
  
  /**
   * End time of the booking
   */
  @Column(nullable = false)
  private LocalDateTime endTime;
  
  /**
   * The room that is booked
   */
  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private RoomEntity roomEntity;
  
  /**
   * The user who made the booking
   */
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity userEntity;
}

