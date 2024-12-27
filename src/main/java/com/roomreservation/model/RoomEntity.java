package com.roomreservation.model;

import com.roomreservation.mapper.BookingMapper;
import com.roomreservation.record.BookingRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_ROOM")
public class RoomEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String name;
  
  @Column(nullable = false)
  private int capacity;
  
  @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BookingEntity> bookingEntities = new ArrayList<>();
  
  public List<BookingRecord> getBookingEntities() {
    return bookingEntities.stream().map(BookingMapper::of).collect(Collectors.toList());
  }
}

