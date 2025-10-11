package knitspace.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import knitspace.exception.ApiException;

/**
 * Base API client that provides common HTTP functionality for all domain
 * clients.
 * Contains shared configuration, error handling, and HTTP utilities.
 */
public class ApiClient {

  protected static final String BASE_URL = "http://localhost:8080"; // Configure as needed
  protected static final ObjectMapper objectMapper = new ObjectMapper();
  protected static final HttpClient httpClient = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(30))
      .build();

  /**
   * Creates a basic HTTP request builder with common headers.
   *
   * @param uri The URI for the request
   * @return HttpRequest.Builder with common configuration
   */
  protected static HttpRequest.Builder createRequestBuilder(String uri) {
    return HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + uri))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .timeout(Duration.ofSeconds(30));
  }

  /**
   * Handles common HTTP response processing and error handling.
   *
   * @param response      The HTTP response
   * @param responseClass The class to deserialize the response to
   * @return The deserialized response object
   * @throws ApiException If the API call fails or returns an error
   */
  protected static <T> T handleResponse(
      HttpResponse<String> response,
      Class<T> responseClass) throws ApiException {

    if (response.statusCode() >= 200 && response.statusCode() < 300) {
      try {
        if (responseClass == Void.class) {
          return null;
        }
        return objectMapper.readValue(response.body(), responseClass);
      } catch (Exception e) {
        throw new ApiException("Failed to parse response: " + e.getMessage(), e);
      }
    } else {
      throw new ApiException(
          "HTTP " + response.statusCode() + ": " + response.body(), response.statusCode());
    }
  }
}