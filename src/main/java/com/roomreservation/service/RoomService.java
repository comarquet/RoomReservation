package com.roomreservation.service;

import com.roomreservation.record.RoomRecord;
import com.roomreservation.model.RoomEntity;
import com.roomreservation.repository.RoomDao;
import com.roomreservation.mapper.RoomMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
  
  private final RoomDao roomDao;
  
  public RoomService(RoomDao roomDao) {
    this.roomDao = roomDao;
  }
  
  public List<RoomRecord> getAllRooms() {
    return roomDao.findAll().stream()
      .map(RoomMapper::of)
      .collect(Collectors.toList());
  }
  
  public RoomRecord getRoomById(Long id) {
    RoomEntity roomEntity = roomDao.findById(id)
      .orElseThrow(() -> new RuntimeException("RoomEntity not found"));
    return RoomMapper.of(roomEntity);
  }
  
  public RoomRecord createRoom(RoomRecord roomRecord) {
    RoomEntity roomEntity = new RoomEntity();
    roomEntity.setName(roomRecord.name());
    roomEntity.setCapacity(roomRecord.capacity());
    RoomEntity savedRoomEntity = roomDao.save(roomEntity);
    return RoomMapper.of(savedRoomEntity);
  }
  
  public RoomRecord updateRoom(Long id, RoomRecord roomRecord) {
    RoomEntity roomEntity = roomDao.findById(id)
      .orElseThrow(() -> new RuntimeException("RoomEntity not found"));
    roomEntity.setName(roomRecord.name());
    roomEntity.setCapacity(roomRecord.capacity());
    RoomEntity updatedRoomEntity = roomDao.save(roomEntity);
    return RoomMapper.of(updatedRoomEntity);
  }
  
  public void deleteRoom(Long id) {
    roomDao.deleteById(id);
  }
}

