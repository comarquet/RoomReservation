package com.roomreservation.api;

import com.roomreservation.mapper.UserMapper;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserCommandRecord;
import com.roomreservation.record.UserRecord;
import com.roomreservation.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * REST Controller for managing users.
 * Provides endpoints for creating, retrieving, updating, and deleting users.
 * Handles user registration and profile management.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private Validator validator;
  
  /**
   * Retrieves all users in the system.
   *
   * @return ResponseEntity containing list of all users
   * @see UserRecord
   */
  @GetMapping
  public ResponseEntity<List<UserRecord>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
  
  /**
   * Retrieves a specific user by their ID.
   *
   * @param id The ID of the user to retrieve
   * @return ResponseEntity containing the requested user
   * @throws RuntimeException if user not found
   * @see UserRecord
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserRecord> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(UserMapper.of(userService.getUserById(id)));
  }
  
  /**
   * Creates a new user.
   * Automatically generates and assigns an access card to the new user.
   *
   * @param userCommand User details for creation
   * @return ResponseEntity containing the created user or error message
   * @throws RuntimeException if email already exists
   * @see UserCommandRecord
   * @see UserRecord
   */
  @PostMapping
  public ResponseEntity<Object> createUser(@Validated @RequestBody UserCommandRecord userCommand) {
    try {
      UserEntity entity = new UserEntity();
      entity.setFirstName(userCommand.firstName());
      entity.setLastName(userCommand.lastName());
      entity.setEmail(userCommand.email());
      entity.setPassword(userCommand.password());
      
      Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);
      if (!violations.isEmpty()) {
        String errorMessage = violations.iterator().next().getMessage();
        if (errorMessage.contains("Email")) {
          return ResponseEntity.badRequest().body("Incorrect email format");
        }
        return ResponseEntity.badRequest().body(errorMessage);
      }
      
      UserEntity createdUser = userService.createUser(entity);
      return ResponseEntity.status(201).body(UserMapper.of(createdUser));
    } catch (RuntimeException e) {
      if (e.getMessage().contains("Email already exists")) {
        return ResponseEntity.badRequest().body("Email already exists");
      }
      return ResponseEntity.badRequest().body("An error occurred while creating the user");
    }
  }
  
  /**
   * Updates an existing user.
   *
   * @param id The ID of the user to update
   * @param userCommand Updated user details
   * @return ResponseEntity containing the updated user
   * @throws RuntimeException if user not found
   */
  @PutMapping("/{id}")
  public ResponseEntity<UserRecord> updateUser(@PathVariable Long id, @Validated @RequestBody UserCommandRecord userCommand) {
    UserEntity entity = new UserEntity();
    entity.setFirstName(userCommand.firstName());
    entity.setLastName(userCommand.lastName());
    entity.setEmail(userCommand.email());
    entity.setPassword(userCommand.password());
    
    UserEntity updatedUser = userService.updateUser(id, entity);
    return ResponseEntity.ok(UserMapper.of(updatedUser));
  }
  
  /**
   * Deletes a user.
   *
   * @param id The ID of the user to delete
   * @return ResponseEntity with no content if deletion successful
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}