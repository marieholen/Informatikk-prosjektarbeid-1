package knitspace.client;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import knitspace.exception.ApiException;
import knitspace.model.User;

/**
 * Client for user-related API operations.
 * Handles authentication, account creation, and user management.
 */
public class UserClient extends ApiClient {

  private static final String USERS_ENDPOINT = "/api/users";

  /**
   * Creates a new user account.
   *
   * @param username The username for the new account
   * @param password The password for the new account
   * @return The created user
   * @throws ApiException If the account creation fails
   */
  public User createAccount(String username, String password) throws ApiException {
    try {
      String requestBody = objectMapper.writeValueAsString(new User(null, username, password));

      HttpRequest request = createRequestBuilder(USERS_ENDPOINT + "/create")
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();

      System.out.println("Sending request to " + USERS_ENDPOINT + "/create");
      HttpResponse<String> response = httpClient.send(request,
          HttpResponse.BodyHandlers.ofString());

      System.out.println("Received response with status code: " + response.statusCode());
      return handleResponse(response, User.class);

    } catch (Exception e) {
      if (e instanceof ApiException) {
        throw (ApiException) e;
      }
      throw new ApiException("Failed to create account: " + e.getMessage(), e);
    }
  }

  /**
   * Asynchronously creates a new user account.
   *
   * @param username The username for the new account
   * @param password The password for the new account
   * @return CompletableFuture containing the created user
   */
  public CompletableFuture<User> createAccountAsync(String username, String password) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return createAccount(username, password);
      } catch (ApiException e) {
        throw new RuntimeException(e);
      }
    });
  }

  /**
   * Logs in a user.
   *
   * @param username The username for the account
   * @param password The password for the account
   * @return The authenticated User object
   * @throws ApiException If the account creation fails
   */
  public User login(String username, String password) throws ApiException {
    try {
      String requestBody = objectMapper.writeValueAsString(new User(null, username, password));

      HttpRequest request = createRequestBuilder(USERS_ENDPOINT + "/login")
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();
      
      System.out.println("Sending request to " + USERS_ENDPOINT + "/login");
      HttpResponse<String> response = httpClient.send(
          request, HttpResponse.BodyHandlers.ofString());

      System.out.println("Received response with status code: " + response.statusCode());
      return handleResponse(response, User.class);
    } catch (Exception e) {
      if (e instanceof ApiException) {
        throw (ApiException) e;
      }
      throw new ApiException("Failed to log in: " + e.getMessage(), e);
    }
  }

  /**
   * Asynchronously logs a user in.
   *
   * @param username The username of user trying to log in
   * @param password The user's password
   * @return CompleteableFuture containing the authenticated user object
   */
  public CompletableFuture<User> loginAsync(String username, String password) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return login(username, password);
      } catch (ApiException e) {
        throw new RuntimeException(e);
      }
    });
  }
}