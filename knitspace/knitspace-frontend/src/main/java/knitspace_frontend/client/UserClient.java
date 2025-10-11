package knitspace_frontend.client;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import knitspace_frontend.model.User;

/**
 * Client for user-related API operations.
 * Handles authentication, account creation, and user management.
 */
public class UserClient extends ApiClient {
    
    private final String USERS_ENDPOINT = "/api/users";
    
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
}