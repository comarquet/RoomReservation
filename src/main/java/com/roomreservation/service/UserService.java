package com.roomreservation.service;

import com.roomreservation.model.UserEntity;
import com.roomreservation.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  
  @Autowired
  private UserDao userDao;
  
  public List<UserEntity> getAllUsers() {
    return userDao.findAll();
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
    userEntity.setRole(userEntityDetails.getRole());
    return userDao.save(userEntity);
  }
  
  public void deleteUser(Long id) {
    userDao.deleteById(id);
  }
}

