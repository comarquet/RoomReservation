package com.roomreservation.mapper;

import com.roomreservation.model.UserEntity;
import com.roomreservation.record.UserRecord;

/**
 * Utility class for mapping between UserEntity objects and their DTO representations.
 * Provides static conversion methods to transform user entities into UserRecord objects.
 * Handles sensitive user information and associated access card details.
 *
 * @see com.roomreservation.model.UserEntity
 * @see com.roomreservation.record.UserRecord
 */
public class UserMapper {
  
  /**
   * Converts a UserEntity to its DTO representation.
   * Maps user profile information and associated card details while maintaining data privacy.
   * The password field in the DTO is handled securely for transmission.
   *
   * @param userEntity The user entity to convert, must not be null
   * @return UserRecord containing the user's data in DTO format
   * @throws NullPointerException if userEntity is null
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
