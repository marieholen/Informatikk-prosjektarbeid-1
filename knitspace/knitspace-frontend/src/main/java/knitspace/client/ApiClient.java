package knitspace.client;

import com.fasterxml.jackson.core.JsonProcessingException;
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
  protected final HttpClient httpClient;

  /**
   * Default ApiClient constructor.
   */
  public ApiClient() {
    this(HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(30))
      .build());
  }

  /**
   * Test constructor to allow mocking.
   * 
   * @param httpClient mock httpClient
   */
  protected ApiClient(HttpClient httpClient) {
    this.httpClient = httpClient;
  }
  
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

  /**
   * Serializes an object to JSON string.
   *
   * @param object The object to serialize
   * @return JSON string representation
   * @throws ApiException If serialization fails
   */
  protected String serialize(Object object) throws ApiException {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new ApiException("Failed to serialize object: " + e.getMessage(), e);
    }
  }

  /**
   * Deserializes a JSON string to an object of the given class (clazz).
   *
   * @param <T> The type of object to deserialize to
   * @param json The JSON string to deserialize
   * @param clazz The class of the object
   * @return The deserialized objcet
   * @throws ApiException If deserialization fails
   */
  protected <T> T deserialize(String json, Class<T> clazz) throws ApiException {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new ApiException("Failed to deserialize JSON: " + e.getMessage(), e);
    }
  }
}