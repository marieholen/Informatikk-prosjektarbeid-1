package knitspace.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple User entity for the Knitspace backend.
 * Based on the core.User from Release 2
 */
@Data
@NoArgsConstructor
public class User {

  /**
   * The unique identifier of the user.
   */
  private UUID userId;

  /**
   * The username of the user.
   */
  private String username;

  /**
   * The password of the user.
   */
  private String password;

  /**
   * Constructor to create a new user with a generated UUID.
   *
   * @param username The username of the user
   * @param password The password of the user
   */
  public User(String username, String password) {
    this.userId = UUID.randomUUID();
    this.username = username;
    this.password = password;
  }

  /**
   * Constructor with all fields.
   *
   * @param userId   The user ID
   * @param username The username of the user
   * @param password The password of the user
   */
  public User(UUID userId, String username, String password) {
    this.userId = userId;
    this.username = username;
    this.password = password;
  }
}