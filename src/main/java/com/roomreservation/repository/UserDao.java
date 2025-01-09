package com.roomreservation.repository;

import com.roomreservation.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing user entities.
 * Provides CRUD operations and custom queries for users.
 * Extends JpaRepository to inherit standard database operations.
 *
 * @see com.roomreservation.model.UserEntity
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface UserDao extends JpaRepository<UserEntity, Long> {
  /**
   * Finds a user by their email address, ignoring case.
   * This method is primarily used for user authentication and email uniqueness validation.
   *
   * @param email the email address to search for (case-insensitive)
   * @return UserEntity if found, null otherwise
   */
  UserEntity findByEmailIgnoreCase(String email);
}
