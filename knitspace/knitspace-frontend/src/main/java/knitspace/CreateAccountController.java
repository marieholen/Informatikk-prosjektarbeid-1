package knitspace;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import knitspace.client.UserClient;
import knitspace.exception.ApiException;

/**
 * Controller for the Create Account page in the frontend application.
 * Handles user registration and validation.
 */
public class CreateAccountController {

  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private PasswordField confirmPasswordField;
  @FXML
  private Button signupButton;

  UserClient userClient;

  /**
   * Initialize the controller and set up event handlers.
   */
  @FXML
  private void initialize() {
    signupButton.setOnAction(this::handleSignup);
    userClient = new UserClient();
  }

  /**
   * Handle the signup button click.
   * Validates input and creates a new user account via API call.
   *
   * @param event The action event
   */
  @FXML
  private void handleSignup(ActionEvent event) {
    String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
    String password = passwordField.getText() == null ? "" : passwordField.getText();
    String confirmPassword = confirmPasswordField.getText() == null
        ? ""
        : confirmPasswordField.getText();

    Optional<String> validationError = validateInput(username, password, confirmPassword);
    if (validationError.isPresent()) {
      showErrorPopup("Validation Error", validationError.get());
      return;
    }

    createUserAccount(username, password);
  }

  /**
   * Creates a new user account.
   * This method will be called by handleSignup after validation.
   * Makes an API call to the backend service to create the account.
   *
   * @param username The username for the new account
   * @param password The password for the new account
   */
  private void createUserAccount(String username, String password) {
    try {
      userClient.createAccount(username, password);

    } catch (ApiException e) {
      String errorMessage;
      if (e.getStatusCode() == 409) {
        errorMessage = "Username already exists. Please choose a different username.";
      } else {
        errorMessage = "Failed to create account: " + e.getMessage();
      }
      showErrorPopup("Account Creation Failed", errorMessage);

    } catch (Exception e) {
      showErrorPopup("Account Creation Failed",
          "An unexpected error occurred. Please try again. Error: " + e.getMessage());
    }
  }

  /**
   * Validates the user input for account creation.
   *
   * @param username        The username to validate
   * @param password        The password to validate
   * @param confirmPassword The password confirmation to validate
   * @return Optional containing error message if validation fails, empty if valid
   */
  Optional<String> validateInput(String username, String password, String confirmPassword) {
    if (username == null || username.isEmpty()) {
      return Optional.of("Username must be filled in.");
    }
    if (password == null || password.isEmpty()) {
      return Optional.of("Password must be filled in.");
    }
    if (confirmPassword == null || confirmPassword.isEmpty()) {
      return Optional.of("Must confirm password.");
    }
    if (password.length() < 6) {
      return Optional.of("Password must be at least 6 characters.");
    }
    if (!password.equals(confirmPassword)) {
      return Optional.of("The passwords do not match.");
    }
    return Optional.empty();
  }

  /**
   * Shows an error alert dialog.
   *
   * @param title   The title of the alert
   * @param message The message to display
   */
  private void showErrorPopup(String title, String message) {
    Platform.runLater(() -> {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
    });
  }
}
