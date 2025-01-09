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

/**
 * Entity representing a room in the system.
 * Maps to the SP_ROOM table in the database and manages room information
 * including capacity and associated bookings.
 *
 * @see BookingEntity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_ROOM")
public class RoomEntity {
  
  /**
   * Unique identifier for the room.
   * Auto-generated using database sequence.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  /**
   * Name or identifier of the room.
   * Used for display and identification purposes.
   */
  @Column(nullable = false)
  private String name;
  
  /**
   * Maximum number of people the room can accommodate.
   * Used for room selection and capacity planning.
   */
  @Column(nullable = false)
  private int capacity;
  
  /**
   * List of all bookings associated with this room.
   * One room can have many bookings (One-to-Many relationship).
   * Cascade operations ensure proper handling of related bookings.
   */
  @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BookingEntity> bookingEntities = new ArrayList<>();
  
  /**
   * Converts the list of booking entities to booking records.
   * Used for API responses and data transfer.
   *
   * @return List of BookingRecord objects representing the room's bookings
   */
  public List<BookingRecord> getBookingEntities() {
    return bookingEntities.stream().map(BookingMapper::of).collect(Collectors.toList());
  }
}

