package com.roomreservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a user in the system.
 * Contains user profile information and associated access card details.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_USER")
public class UserEntity {
  
  /**
   * Unique identifier for the user
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;
  
  /**
   * User's first name
   */
  @NotNull
  private String firstName;
  
  /**
   * User's last name
   */
  @NotNull
  private String lastName;
  
  /**
   * User's email address (must be unique)
   */
  @NotNull
  @Email(message = "Incorrect email format")
  @Column(unique = true)
  private String email;
  
  /**
   * User's password
   */
  @NotNull
  private String password;
  
  /**
   * User's associated access card
   */
  @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private CardEntity cardEntity;
}