package com.roomreservation.repository;

import com.roomreservation.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<RoomEntity, Long> { }
