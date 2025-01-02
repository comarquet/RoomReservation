package com.roomreservation.mapper;

import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserRecord;

/**
 * Utility class for mapping between User entities and DTOs.
 * Provides conversion methods to transform UserEntity objects to UserRecord objects.
 */
public class UserMapper {
  
  /**
   * Converts a UserEntity to its DTO representation.
   * @param userEntity The user entity to convert
   * @return UserRecord containing the user's data
   */
  public static UserRecord of(UserEntity userEntity) {
    return new UserRecord(
      userEntity.getId(),
      userEntity.getFirstName(),
      userEntity.getLastName(),
      userEntity.getEmail(),
      userEntity.getPassword(),
      userEntity.getCardEntity() != null ? userEntity.getCardEntity().getId() : null
    );
  }
}
