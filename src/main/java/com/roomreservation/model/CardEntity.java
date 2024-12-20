package com.roomreservation.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SP_CARD")
public class CardEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, unique = true)
  private String cardNumber;
  
  private boolean active;
  
  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, unique = true)
  private UserEntity userEntity;
}

