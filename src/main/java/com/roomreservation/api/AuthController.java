package com.roomreservation.api;

import com.roomreservation.mapper.UserMapper;
import com.roomreservation.model.UserEntity;
import com.roomreservation.record.LoginRequestRecord;
import com.roomreservation.record.UserRecord;
import com.roomreservation.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for authentication operations.
 * Handles user login and authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UserService userService;
  
  public AuthController(UserService userService) {
    this.userService = userService;
  }
  
  /**
   * Authenticates a user's login credentials.
   *
   * @param loginRequest Contains user email and password
   * @return ResponseEntity containing UserRecord if authentication is successful
   * @throws RuntimeException if credentials are invalid
   * @see LoginRequestRecord
   * @see UserRecord
   */
  @PostMapping("/login")
  public ResponseEntity<UserRecord> login(@RequestBody LoginRequestRecord loginRequest) {
    UserEntity user = userService.validateLogin(loginRequest.email(), loginRequest.password());
    return ResponseEntity.ok(UserMapper.of(user));
  }
}