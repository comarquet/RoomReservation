package com.roomreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an access card in the system.
 * Maps to the SP_CARD table in the database and manages access credentials
 * for users to enter rooms.
 *
 * @see UserEntity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_CARD")
public class CardEntity {
  
  /**
   * Unique identifier for the card.
   * Auto-generated using database sequence.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  /**
   * Unique card number assigned to this card.
   * Used for physical access control and identification.
   * Must be unique across all cards in the system.
   */
  @Column(nullable = false, unique = true)
  private String cardNumber;
  
  /**
   * The user who owns this card.
   * One-to-One relationship with UserEntity as each user can have only one card
   * and each card belongs to exactly one user.
   */
  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private UserEntity userEntity;
}

