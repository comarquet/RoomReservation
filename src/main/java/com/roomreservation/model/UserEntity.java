package com.roomreservation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class UserEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotNull
  @Size(min = 2, max = 50)
  private String firstName;
  
  @NotNull
  @Size(min = 2, max = 50)
  private String lastName;
  
  @NotNull
  @Email
  @Column(unique = true)
  private String email;
  
  @NotNull
  @Size(min = 6, max = 100)
  private String password;
  
  private String role;
  
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private CardEntity cardEntity;
}
