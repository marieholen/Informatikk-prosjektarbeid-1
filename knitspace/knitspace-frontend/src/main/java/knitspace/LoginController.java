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

  public LoginController() {
    this.userClient = new UserClient();
  }

  @FXML
  private void initialize() {
    createAccountLink.setOnAction(this::handleCreateAccount);
    loginButton.setOnAction(this::handleLogin);
  }

  @FXML
  private void handleCreateAccount(ActionEvent event) {
    try {
      goToCreateAccount();
    } catch (Exception e) {
      showAlert("Navigation error", "Could not open the CreateAccount page." + e.getMessage());
    }
  }

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

  private String friendlyError(Throwable ex) {
    Throwable cause = ex;
    while (cause.getCause() != null) cause = cause.getCause();
    if (cause instanceof ApiException a) {
      return a.getMessage() != null ? a.getMessage() : "Invalid username or password.";
    }
    String msg = cause.getMessage();
    if (msg != null && msg.contains("401")) {
      return "Invalid username or password";
    }
    return (msg == null || msg.isBlank()) ? "Something went wrong. Try again." : msg;
  }

  private void setUiBusy(boolean busy) {
    loginButton.setDisable(busy);
    createAccountLink.setDisable(busy);
    usernameField.setDisable(busy);
    passwordField.setDisable(busy);
  }

  private void goToCreateAccount() throws IOException {
    Stage stage = (Stage) loginButton.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
        "/knitspace/CreateAccount.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Create Account");
  }

  private void goToMyRecipes() throws IOException {
    Stage stage = (Stage) loginButton.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("My Recipes");
  }

  private void showAlert(String title, String message) {
    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
