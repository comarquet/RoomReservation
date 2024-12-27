package com.roomreservation.repository;

import com.roomreservation.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {
  UserEntity findByEmailIgnoreCase(String email);
}
