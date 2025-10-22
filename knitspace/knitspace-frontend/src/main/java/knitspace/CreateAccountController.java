package knitspace;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
  @FXML
  private Hyperlink loginLink;
  private UserClient userClient;

  /**
   * Default constructor - creates UserClient instance.
   */
  public CreateAccountController() {
    this.userClient = new UserClient();
  }

  /**
   * Constructor for dependency injection (used in testing when mocking
   * UserClient).
   *
   * @param userClient The UserClient to use for API calls
   */
  public CreateAccountController(UserClient userClient) {
    this.userClient = userClient;
  }

  /**
   * Initialize the controller and set up event handlers.
   */
  @FXML
  private void initialize() {
    signupButton.setOnAction(this::handleSignup);
    loginLink.setOnAction(this::handleLogIn);
  }

  /**
   * Handle the signup button click.
   * Validates input and creates a new user account via API call.
   *
   * @param event The action event
   */
  @FXML
  void handleSignup(ActionEvent event) {
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
      goToMyRecipes(signupButton);

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
   * Navigates to the MyRecipes page.
   *
   * @param anyNodeInCurrentScene Any node in the current scene to get the stage from
   */
  void goToMyRecipes(Node anyNodeInCurrentScene) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));
      Scene scene = new Scene(loader.load());

      Stage stage = (Stage) anyNodeInCurrentScene.getScene().getWindow();
      stage.setScene(scene);
    } catch (Exception e) {
      e.printStackTrace();
      showErrorPopup("Navigation error", "Could not open the MyRecipes page.");
    }
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

  /**
   * Handles navigation to the login page when the login link is clicked.
   */
  @FXML
  void handleLogIn(ActionEvent event) {
    goToLogin(loginLink);
  }

  /**
   * Navigates the user to the login page.
   *
   * @param anyNodeInCurrentScene a reference to any node in the current scene,
   * used to obtain the current {@link Stage}
   */
  private void goToLogin(Node anyNodeInCurrentScene) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/Login.fxml"));
      Scene scene = new Scene(loader.load());

      Stage stage = (Stage) anyNodeInCurrentScene.getScene().getWindow();
      stage.setScene(scene);
    } catch (Exception e) {
      e.printStackTrace();
      showErrorPopup("Navigation error", "Could not open the Login page.");
    }
  }
}
