package com.roomreservation.repository;

import com.roomreservation.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing room entities.
 * Provides CRUD operations for rooms.
 * Extends JpaRepository to inherit standard database operations.
 *
 * @see com.roomreservation.model.RoomEntity
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface RoomDao extends JpaRepository<RoomEntity, Long> { }
