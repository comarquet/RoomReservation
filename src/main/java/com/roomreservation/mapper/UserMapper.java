package com.roomreservation.mapper;

import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserRecord;

public class UserMapper {
  public static UserRecord of(UserEntity userEntity) {
    return new UserRecord(
      userEntity.getId(),
      userEntity.getFirstName(),
      userEntity.getLastName(),
      userEntity.getEmail(),
      userEntity.getPassword(),
      userEntity.getCardEntity().getId()
    );
  }
}
