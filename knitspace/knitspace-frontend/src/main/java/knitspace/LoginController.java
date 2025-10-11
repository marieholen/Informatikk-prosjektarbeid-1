package knitspace;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * Controller for Login.fxml.
 */
public class LoginController {
  @FXML
  private Hyperlink createAccountLink;

  @FXML
  private Button loginButton;

  @FXML
  private void initialize() {
    createAccountLink.setOnAction(this::handleCreateAccount);
  }

  @FXML
  private void handleCreateAccount(ActionEvent event) {
    try {
      goToCreateAccount();
    } catch (Exception e) {
      showAlert("Navigation error", "Could not open the CreateAccount page." + e.getMessage());
    }
  }

  private void goToCreateAccount() throws IOException {
    Stage stage = (Stage) loginButton.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
        "/knitspace_frontend/CreateAccount.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Create Account");
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
