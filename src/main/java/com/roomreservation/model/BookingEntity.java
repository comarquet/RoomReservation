package com.roomreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_BOOKING")
public class BookingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private LocalDateTime startTime;
  
  @Column(nullable = false)
  private LocalDateTime endTime;
  
  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private RoomEntity roomEntity;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity userEntity;
}

