package com.roomreservation.service;

import com.roomreservation.mapper.UserMapper;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserRecord;
import com.roomreservation.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
  
  public UserEntity createUser(UserEntity userEntity) {
    return userDao.save(userEntity);
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

