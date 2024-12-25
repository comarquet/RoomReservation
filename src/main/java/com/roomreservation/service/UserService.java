package com.roomreservation.service;

import com.roomreservation.mapper.UserMapper;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserRecord;
import com.roomreservation.repository.UserDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {
  
  @Autowired
  private UserDao userDao;
  
  public List<UserRecord> getAllUsers() {
    return userDao.findAll().stream()
      .map(UserMapper::of)
      .collect(Collectors.toList());
  }
  
  public UserEntity getUserById(Long id) {
    return userDao.findById(id).orElseThrow(() -> new RuntimeException("UserEntity not found"));
  }
  
  @Transactional
  public UserEntity createUser(UserEntity userEntity) {
    // Check if email already exists
    UserEntity existingUser = userDao.findByEmail(userEntity.getEmail());
    if (existingUser != null) {
      throw new RuntimeException("Email already exists");
    }
    
    // Ensure new entity
    userEntity.setId(null);
    userEntity.setCardEntity(null);
    
    try {
      return userDao.save(userEntity);
    } catch (DataIntegrityViolationException e) {
      throw new RuntimeException("Failed to create user: " + e.getMessage());
    }
  }
  
  public UserEntity updateUser(Long id, UserEntity userEntityDetails) {
    UserEntity userEntity = getUserById(id);
    userEntity.setFirstName(userEntityDetails.getFirstName());
    userEntity.setLastName(userEntityDetails.getLastName());
    userEntity.setEmail(userEntityDetails.getEmail());
    userEntity.setPassword(userEntityDetails.getPassword());
    return userDao.save(userEntity);
  }
  
  public void deleteUser(Long id) {
    userDao.deleteById(id);
  }
}

