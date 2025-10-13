package knitspace.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import knitspace.model.User;
import knitspace.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for UserService.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach
  void setUp() {
    testUser = new User(UUID.randomUUID(), "testuser", "testpass");
  }

  @Test
  void authenticateUser_ValidCredentials_ReturnsUser() throws IOException {
    when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

    Optional<User> result = userService.authenticateUser("testuser", "testpass");

    assertTrue(result.isPresent());
    assertEquals(testUser, result.get());
  }

  @Test
  void authenticateUser_InvalidCredentials_ReturnsEmpty() throws IOException {
    when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

    Optional<User> result = userService.authenticateUser("wronguser", "wrongpass");

    assertFalse(result.isPresent());
  }

  @Test
  void authenticateUser_RepositoryThrowsIOException_PropagatesException() throws IOException {
    when(userRepository.findAll()).thenThrow(new IOException("Database error"));

    assertThrows(IOException.class,
        () -> userService.authenticateUser("testuser", "testpass"));
  }

  @Test
  void createUser_NewUsername_ReturnsCreatedUser() throws IOException {
    when(userRepository.existsByUsername("newuser")).thenReturn(false);
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    User result = userService.createUser("newuser", "newpass");

    assertNotNull(result);
    assertEquals("newuser", result.getUsername());
    assertEquals("newpass", result.getPassword());
    assertNotNull(result.getUserId());
  }

  @Test
  void createUser_ExistingUsername_ThrowsException() throws IOException {
    when(userRepository.existsByUsername("testuser")).thenReturn(true);

    assertThrows(IllegalArgumentException.class,
        () -> userService.createUser("testuser", "testpass"));
  }

  @Test
  void createUser_RepositoryThrowsIOException_PropagatesException() throws IOException {
    when(userRepository.existsByUsername("newuser")).thenThrow(new IOException("Database error"));

    assertThrows(IOException.class,
        () -> userService.createUser("newuser", "newpass"));
  }
}