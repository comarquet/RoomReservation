package com.roomreservation.model;

import com.roomreservation.mapper.BookingMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.roomreservation.record.BookingRecord;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false)
  private int capacity;
  
  private boolean available;
  
  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BookingEntity> bookingEntities = new ArrayList<>();
  
  public List<BookingRecord> getBookingEntities() {
    return bookingEntities.stream().map(BookingMapper::of).collect(Collectors.toList());
  }
}

