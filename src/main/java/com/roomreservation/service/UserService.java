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

/**
 * Service managing user operations in the system.
 * Handles user creation, authentication, and profile management.
 * Also manages the relationship between users and their access cards.
 */
@Transactional
@Service
public class UserService {
  private final UserDao userDao;
  private final CardDao cardDao;
//  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  
  public UserService(UserDao userDao, CardDao cardDao) {
    this.userDao = userDao;
    this.cardDao = cardDao;
  }
  
  /**
   * Retrieves all users in the system.
   *
   * @return List of UserRecord representing all users
   */
  public List<UserRecord> getAllUsers() {
    return userDao.findAll().stream()
      .map(UserMapper::of)
      .collect(Collectors.toList());
  }
  
  /**
   * Retrieves a specific user by their ID.
   *
   * @param id The ID of the user to retrieve
   * @return UserEntity representing the requested user
   * @throws RuntimeException if user not found
   */
  public UserEntity getUserById(Long id) {
    return userDao.findById(id).orElseThrow(() -> new RuntimeException("UserEntity not found"));
  }
  
  /**
   * Creates a new user in the system.
   * Automatically generates and assigns an access card to the new user.
   *
   * @param userEntity User details including name, email, and password
   * @return UserEntity representing the created user with generated card
   * @throws RuntimeException if email already exists
   */
  @Transactional
  public UserEntity createUser(UserEntity userEntity) {
    if (userDao.findByEmailIgnoreCase(userEntity.getEmail()) != null) {
      throw new RuntimeException("Email already exists");
    }
    
    userEntity.setId(null);
//    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    userEntity.setPassword(userEntity.getPassword());
    UserEntity savedUser = userDao.save(userEntity);
    
    CardEntity cardEntity = new CardEntity();
    cardEntity.setCardNumber("CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    cardEntity.setUserEntity(savedUser);
    
    savedUser.setCardEntity(cardEntity);
    cardDao.save(cardEntity);
    
    return userDao.save(savedUser);
  }
  
  private String generateCardNumber() {
    return "CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
  }
  
  /**
   * Updates an existing user's information.
   *
   * @param id ID of the user to update
   * @param userEntityDetails New user details
   * @return UserEntity representing the updated user
   * @throws RuntimeException if user not found
   */
  public UserEntity updateUser(Long id, UserEntity userEntityDetails) {
    UserEntity userEntity = getUserById(id);
    userEntity.setFirstName(userEntityDetails.getFirstName());
    userEntity.setLastName(userEntityDetails.getLastName());
    userEntity.setEmail(userEntityDetails.getEmail());
    userEntity.setPassword(userEntityDetails.getPassword());
    return userDao.save(userEntity);
  }
  
  /**
   * Deletes a user from the system.
   *
   * @param id ID of the user to delete
   */
  public void deleteUser(Long id) {
    userDao.deleteById(id);
  }
  
  /**
   * Validates user login credentials.
   * Currently uses plain text password comparison.
   *
   * @param email User's email address
   * @param password User's password
   * @return UserEntity if validation succeeds
   * @throws RuntimeException if credentials are invalid
   */
  public UserEntity validateLogin(String email, String password) {
    UserEntity user = userDao.findByEmailIgnoreCase(email);
//    if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
    if (user == null || !password.equals(user.getPassword())) {
      throw new RuntimeException("Invalid email or password");
    }
    return user;
  }
}

