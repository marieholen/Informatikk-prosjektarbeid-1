package knitspace.exception;

/**
 * Custom exception for API-related errors.
 */
public class ApiException extends Exception {
  private final int statusCode;

  /**
   * Constructor with only error message.
   */
  public ApiException(String message) {
    super(message);
    this.statusCode = -1;
  }

  /**
   * Constructor with error message and satatus code.
   */
  public ApiException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  /**
   * Constructor with error message and cause.
   */
  public ApiException(String message, Throwable cause) {
    super(message, cause);
    this.statusCode = -1;
  }

  /*
   * Getter for status code
   */
  public int getStatusCode() {
    return statusCode;
  }

  /*
   * Checks if the error is client side
   */
  public boolean isClientError() {
    return statusCode >= 400 && statusCode < 500;
  }

  /*
   * Checks if the error is server side
   */
  public boolean isServerError() {
    return statusCode >= 500;
  }
}
