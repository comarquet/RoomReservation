package com.roomreservation.model;

import jakarta.persistence.*;
import lombok.*;
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
  
  private boolean active;
  
  @ManyToOne
  @JoinColumn(name = "room", nullable = false)
  private RoomEntity roomEntity;
  
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity userEntity;
}

