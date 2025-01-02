package com.roomreservation;

import com.roomreservation.model.CardEntity;
import com.roomreservation.model.UserEntity;
import com.roomreservation.repository.CardDao;
import com.roomreservation.repository.UserDao;
import com.roomreservation.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserDao userDao;
  @Mock
  private CardDao cardDao;
  
  @InjectMocks
  private UserService userService;
  
  private UserEntity mockUser;
  private CardEntity mockCard;
  
  @BeforeEach
  void setUp() {
    mockUser = new UserEntity();
    mockUser.setId(1L);
    mockUser.setFirstName("Test");
    mockUser.setLastName("User");
    mockUser.setEmail("test@test.com");
    mockUser.setPassword("password");
    
    mockCard = new CardEntity();
    mockCard.setId(1L);
    mockCard.setCardNumber("CARD-TEST001");
    mockCard.setUserEntity(mockUser);
    
    mockUser.setCardEntity(mockCard);
  }
  
  @Test
  void createUser_Success() {
    when(userDao.findByEmailIgnoreCase(mockUser.getEmail())).thenReturn(null);
    when(userDao.save(any(UserEntity.class))).thenReturn(mockUser);
    when(cardDao.save(any(CardEntity.class))).thenReturn(mockCard);
    
    UserEntity result = userService.createUser(mockUser);
    
    assertNotNull(result);
    assertEquals(mockUser.getEmail(), result.getEmail());
    assertNotNull(result.getCardEntity());
  }
  
  @Test
  void createUser_EmailExists() {
    when(userDao.findByEmailIgnoreCase(mockUser.getEmail())).thenReturn(mockUser);
    
    assertThrows(RuntimeException.class, () -> userService.createUser(mockUser));
  }
}