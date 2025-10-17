package knitspace.meta;

import knitspace.model.User;

/**
 * Manages the current user session in the application.
 */
public class SessionManager {
  private static User currentUser;

  /**
   * Sets the current user for the session.
   *
   * @param user the {@link User} to set as the current user for the session
   */
  public static void setCurrentUser(User user) {
    if (user == null) {
      currentUser = null;
    } else {
      currentUser = new User(user.userId(), user.username(), user.password());
    }
  }

  /**
   * Retrieves the current user of the session.
   *
   * @return a new {@link User} instance representing the current user, or
   *         {@code null} if no user is set
   */
  public static User getCurrentUser() {
    if (currentUser == null) {
      return null;
    }
    return new User(currentUser.userId(), currentUser.username(), currentUser.password());
  }
}
