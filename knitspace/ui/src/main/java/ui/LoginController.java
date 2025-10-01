package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import core.User;
import persistency.UserPersistence;

import java.io.IOException;
import java.util.List;

public class LoginController {

    @FXML TextField usernameField;

    @FXML PasswordField passwordField;

    @FXML Button loginButton;

    @FXML Hyperlink createAccountLink;

    @FXML
    private void initialize() {
        loginButton.setOnAction(this::handleLogin);

        usernameField.setOnAction(this::handleLogin);
        passwordField.setOnAction(this::handleLogin);
        
        createAccountLink.setOnAction(this::handleCreateAccount);

    }

    @FXML void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            showAlert("Input Error", "Please enter a username.");
            return;
        }

        if (password.isEmpty()) {
            showAlert("Input Error", "Please enter a password.");
            return;
        }

        if (authenticateUser(username, password)) {
            try {
                goToMyRecipes();
            } catch (IOException e) {
                showAlert("Navigation Error", "Failed to load the main application: " + e.getMessage());
            }
        } else {
            showAlert("Login Failed", "Invalid username or password. Please try again.");
        }
    }

    boolean authenticateUser(String username, String password) {
        try {
            List<User> users = UserPersistence.getAllUsers();
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            showAlert("Authentication Error", "Failed to verify credentials: " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void handleCreateAccount (ActionEvent event) {
        try {
        goToCreateAccount();
        }
        catch (Exception e) {
            showAlert("Navigation error", "Could not open the CreateAccount page." + e.getMessage());
        }
    }

    void goToMyRecipes() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyRecipes.fxml"));
        Parent root = fxmlLoader.load();

        // Pass user information to the main controller if needed
        // AppController controller = fxmlLoader.getController();
        // controller.setCurrentUser(currentUser);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("KnitSpace - My Recipes");
    }

    private void goToCreateAccount() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create Account");
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

