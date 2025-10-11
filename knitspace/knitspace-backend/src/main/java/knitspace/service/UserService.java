package knitspace.service;

import java.io.IOException;
import java.util.Optional;
import knitspace.model.User;
import knitspace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer for User operations.
 * Implements business logic for user management
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  /**
   * Authenticate user by username and password.
   *
   * @param username The username
   * @param password The password
   * @return Optional containing the user if authentication is successful
   */
  public Optional<User> authenticateUser(String username, String password) throws IOException {
    return userRepository.findAll().stream()
        .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
        .findFirst();
  }

  /**
   * Create a new user.
   *
   * @param username The username
   * @param password The password
   * @return The created user
   */
  public User createUser(
      String username, String password) throws IllegalArgumentException, IOException {
    User user = new User(username, password);
    if (userRepository.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already exists");
    }
    return userRepository.save(user);
  }
}