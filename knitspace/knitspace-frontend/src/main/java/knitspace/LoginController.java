package knitspace;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import knitspace.client.UserClient;
import knitspace.exception.ApiException;
import knitspace.meta.SessionManager;
import knitspace.model.User;

/**
 * Controller for Login.fxml.
 */
public class LoginController {
  @FXML private Hyperlink createAccountLink;
  @FXML private Button loginButton;
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;

  private final UserClient userClient;

  /**
   * Constructor for dependency injection which (used in testing when mocking UserClient).
   *
   * @param userClient The UserClient to use for API calls
   */
  public LoginController(UserClient userClient) {
    this.userClient = userClient;
  }

  /**
   * Default constructor - initializes the controller by creating a new UserClient instance.
   */
  public LoginController() {
    this(new UserClient());
  }

  /**
   * Initializes the controller and sets up event handlers.
   */
  @FXML
  private void initialize() {
    createAccountLink.setOnAction(this::handleCreateAccount);
    loginButton.setOnAction(this::handleLogin);
  }

  /**
   * Handles the "Create Account" hyprlink action.
   * Navigates to the create Account page.
   *
   * @param event Triggered by clicking the create account button
   */
  @FXML
  private void handleCreateAccount(ActionEvent event) {
    try {
      goToCreateAccount();
    } catch (Exception e) {
      showAlert("Navigation error", "Could not open the CreateAccount page." + e.getMessage());
    }
  }

  /**
   * Handles the login button action.
   * Validates user input, attempts to authenticate asynchronously.
   * Navigates to the "My Recipes" page if successful.
   *
   * @param event Triggered by clicking the login button
   */
  @FXML
  private void handleLogin(ActionEvent event) {
    final String username = usernameField.getText();
    final String password = passwordField.getText();

    if (username == null || username.isBlank() || password == null || password.isBlank()) {
      showAlert("Login failed", "Please enter both username and password.");
      return;
    }

    setUiBusy(true);

    CompletableFuture<User> fut = userClient.loginAsync(username, password);

    fut.thenAccept(user -> Platform.runLater(() -> {
      setUiBusy(false);
      try {
        User userToBeLoggedIn = fut.get();

        SessionManager.setCurrentUser(userToBeLoggedIn);

        goToMyRecipes();
      } catch (Exception e) {
        showAlert("Navigation error", "Could not open MyRecipes." + e.getMessage());
      }
    })).exceptionally(ex -> {
      Platform.runLater(() -> {
        setUiBusy(false);
        String msg = friendlyError(ex);
        showAlert("Login failed", msg);
      });
      return null;
    });
  }

  /**
   * Converts an exception into a user-friendly error message.
   *
   * @param ex The thrown exception
   * @return An error message if validation fails
   */
  private String friendlyError(Throwable ex) {
    Throwable cause = ex;
    while (cause.getCause() != null) {
      cause = cause.getCause();
    }
    if (cause instanceof ApiException) {
      ApiException a = (ApiException) cause;
      if (a.getMessage().contains("401") || a.getMessage() == null) {
        return "Invalid username or password.";
      } else {
        return a.getMessage();
      }
    }
    String msg = cause.getMessage();
    if (msg != null && msg.contains("401")) {
      return "Invalid username or password";
    }
    return (msg == null || msg.isBlank()) ? "Something went wrong. Try again." : msg;
  }

  /**
   * Enables or disables all UI elements to indicate that a background process is running.
   *
   * @param busy Disables input fields and buttons
   */
  private void setUiBusy(boolean busy) {
    loginButton.setDisable(busy);
    createAccountLink.setDisable(busy);
    usernameField.setDisable(busy);
    passwordField.setDisable(busy);
  }

  /**
   * Navigates to the Create Account scene.
   *
   * @throws IOException If the FXML can't be loaded
   */
  private void goToCreateAccount() throws IOException {
    Stage stage = (Stage) loginButton.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
        "/knitspace/CreateAccount.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Create Account");
  }

  /**
   * Navigates to the My Recipes scene.
   *
   * @throws IOException If the FXML file can't be loaded
   */
  private void goToMyRecipes() throws IOException {
    Stage stage = (Stage) loginButton.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("My Recipes");
  }

  /**
   * Displays an alert with title and message.
   *
   * @param title Title of alert dialog
   * @param messgae Message of alert dialog
   */
  private void showAlert(String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}

