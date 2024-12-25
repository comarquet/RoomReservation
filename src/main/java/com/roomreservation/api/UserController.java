package com.roomreservation.api;

import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserRecord;
import com.roomreservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @GetMapping
  public ResponseEntity<List<UserRecord>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
    UserEntity userEntity = userService.getUserById(id);
    return ResponseEntity.ok(userEntity);
  }
  
  @PostMapping
  public ResponseEntity<UserEntity> createUser(@Validated @RequestBody UserEntity userEntity) {
    UserEntity newUserEntity = userService.createUser(userEntity);
    return ResponseEntity.status(201).body(newUserEntity);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @Validated @RequestBody UserEntity userEntityDetails) {
    UserEntity updatedUserEntity = userService.updateUser(id, userEntityDetails);
    return ResponseEntity.ok(updatedUserEntity);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}

