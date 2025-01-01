package com.roomreservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_USER")
public class UserEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;
  
  @NotNull
  private String firstName;
  
  @NotNull
  private String lastName;
  
  @NotNull
  @Email(message = "Incorrect email format")
  @Column(unique = true)
  private String email;
  
  @NotNull
  private String password;
  
  @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private CardEntity cardEntity;
}