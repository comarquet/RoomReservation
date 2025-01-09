package com.roomreservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user in the system.
 * Maps to the SP_USER table in the database and manages user information
 * including personal details and access credentials.
 *
 * @see CardEntity
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_USER")
public class UserEntity {
  
  /**
   * Unique identifier for the user.
   * Auto-generated using database sequence.
   * Cannot be updated once set.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;
  
  /**
   * User's first name.
   * Required field for user identification.
   */
  @NotNull
  private String firstName;
  
  /**
   * User's last name.
   * Required field for user identification.
   */
  @NotNull
  private String lastName;
  
  /**
   * User's email address.
   * Must be unique in the system and properly formatted.
   * Used for login and communication purposes.
   */
  @NotNull
  @Email(message = "Incorrect email format")
  @Column(unique = true)
  private String email;
  
  /**
   * User's password.
   * Required for authentication.
   * Should be stored in encrypted format.
   */
  @NotNull
  private String password;
  
  /**
   * User's associated access card.
   * One-to-One relationship with CardEntity.
   * Cascade operations ensure proper handling of the associated card.
   */
  @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private CardEntity cardEntity;
}