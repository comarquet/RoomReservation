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

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UserService userService;
  
  public AuthController(UserService userService) {
    this.userService = userService;
  }
  
  @PostMapping("/login")
  public ResponseEntity<UserRecord> login(@RequestBody LoginRequestRecord loginRequest) {
    UserEntity user = userService.validateLogin(loginRequest.email(), loginRequest.password());
    return ResponseEntity.ok(UserMapper.of(user));
  }
}