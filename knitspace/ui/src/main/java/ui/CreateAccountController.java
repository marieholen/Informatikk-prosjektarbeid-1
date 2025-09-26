package ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import persistency.UserPersistence;
import core.User;

public class CreateAccountController {
    @FXML
    Button signupButton;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField, confirmPasswordField;

    @FXML
    private void initialize() {
        signupButton.setOnAction(e -> onSignup());
    }

    private void onSignup() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String confirmPassword = confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText();

        Optional<String> validationError = validateInput(username, password, confirmPassword);

        if (validationError.isPresent()) {
            showErrorPopup("Validation error", validationError.get());
            return;
        }
        try {
            List<User> users = UserPersistence.getAllUsers();
            boolean exists = users.stream()
                    .map(User::getUsername)
                    .collect(Collectors.toSet())
                    .contains(username);
            if (exists) {
                showErrorPopup("Username already exists", "Choose a different username.");
                return;
            }

            User newUser = new User(username, password);
            UserPersistence.addUser(newUser);

            showInfoPopup("User created", "The user was created. You will now be logged in.");
            goToMyPatterns(signupButton);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("System error", "Something went wrong while saving. Please try again.");
        }

    }

    private Optional<String> validateInput(String username, String password, String confirmPassword) {
        if (username.isEmpty())
            return Optional.of("Username must be filled in.");
        if (password.isEmpty())
            return Optional.of("Password must be filled in.");
        if (confirmPassword.isEmpty())
            return Optional.of("Must confirm password.");
        if (password.length() < 6)
            return Optional.of("Password must be at least 6 characters.");
        if (!password.equals(confirmPassword))
            return Optional.of("The passwords do not match.");
        return Optional.empty();
    }

    private void showErrorPopup(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showInfoPopup(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void goToMyPatterns(Node anyNodeInCurrentScene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MyPatterns.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) anyNodeInCurrentScene.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("Navigation error", "Could not open the MyPatterns page.");
        }
    }

}
