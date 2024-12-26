package com.roomreservation.service;

import com.roomreservation.mapper.UserMapper;
import com.roomreservation.model.CardEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserRecord;
import com.roomreservation.repository.CardDao;
import com.roomreservation.repository.UserDao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {
  private final UserDao userDao;
  private final CardDao cardDao;
  
  public UserService(UserDao userDao, CardDao cardDao) {
    this.userDao = userDao;
    this.cardDao = cardDao;
  }
  
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
    if (userDao.findByEmail(userEntity.getEmail()) != null) {
      throw new RuntimeException("Email already exists");
    }
    
    userEntity.setId(null);
    UserEntity savedUser = userDao.save(userEntity);
    
    CardEntity cardEntity = new CardEntity();
    cardEntity.setCardNumber("CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    cardEntity.setActive(true);
    cardEntity.setUserEntity(savedUser);
    
    savedUser.setCardEntity(cardEntity);
    cardDao.save(cardEntity);
    
    return userDao.save(savedUser);
  }
  
  private String generateCardNumber() {
    return "CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
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
  
  public UserEntity validateLogin(String email, String password) {
    UserEntity user = userDao.findByEmail(email);
    if (user == null || !user.getPassword().equals(password)) {
      throw new RuntimeException("Invalid email or password");
    }
    return user;
  }
}

