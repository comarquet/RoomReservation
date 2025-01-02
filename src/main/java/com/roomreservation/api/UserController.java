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

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private Validator validator;
  
  @GetMapping
  public ResponseEntity<List<UserRecord>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<UserRecord> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok(UserMapper.of(userService.getUserById(id)));
  }
  
  @PostMapping
  public ResponseEntity<Object> createUser(@Validated @RequestBody UserCommandRecord userCommand) {
    try {
      UserEntity entity = new UserEntity();
      entity.setFirstName(userCommand.firstName());
      entity.setLastName(userCommand.lastName());
      entity.setEmail(userCommand.email());
      entity.setPassword(userCommand.password());
      
      // Validate the entity
      Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);
      if (!violations.isEmpty()) {
        // Get the first validation error message
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
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}