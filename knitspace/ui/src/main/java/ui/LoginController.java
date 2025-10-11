package ui;

import java.io.IOException;
import java.util.List;

import core.SessionManager;
import core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistency.UserPersistence;

public class LoginController {

    @FXML TextField usernameField;

    @FXML PasswordField passwordField;

    @FXML Button loginButton;

    @FXML Hyperlink createAccountLink;

    /**
     * Initializes the login controller by setting up event handlers for UI components.
     */
    @FXML
    private void initialize() {
        loginButton.setOnAction(this::handleLogin);

        usernameField.setOnAction(this::handleLogin);
        passwordField.setOnAction(this::handleLogin);
        
        createAccountLink.setOnAction(this::handleCreateAccount);

    }

    /**
     * Handles the login action when the user clicks the login button.
     * 
     * Validates user input, authenticates the user and navigates to the main application if user is valid
     * 
     * @param event triggered by the login button or text field action
     */
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

            User authenticatedUser = authenticateUser(username, password);
            if (authenticatedUser != null) {
                SessionManager.setCurrentUser(authenticatedUser);
            try {
                goToMyRecipes();
            } catch (IOException e) {
                showAlert("Navigation Error", "Failed to load the main application: " + e.getMessage());
            }
        } else {
            showAlert("Login Failed", "Invalid username or password. Please try again.");
        }
    }

    /**
     * Authenticates a user based on their provided username and password.
     * 
     * This method checks the stored user data in {@link UserPersistence} for a matching username and password combination
     * 
     * @param username
     * @param password
     * @return a {@link User} object if authentication succeeds, or {@code null} if it fails
     */
    public User authenticateUser(String username, String password) {
        try {
            List<User> users = UserPersistence.getAllUsers();
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        } catch (Exception e) {
            showAlert("Authentication Error", "Failed to verify credentials: " + e.getMessage());
        }
        return null;
    }

    /**
     * Handles the "Create Account" link action.
     * 
     * Navigates the user to the Create Account page
     * 
     * @param event triggered by clicking "Create Account"
     */
    @FXML
    private void handleCreateAccount (ActionEvent event) {
        try {
        goToCreateAccount();
        }
        catch (Exception e) {
            showAlert("Navigation error", "Could not open the CreateAccount page." + e.getMessage());
        }
    }

    /**
     * Navigates the user to the "My Recipes" view after a successful login.
     * 
     * @throws IOException if the FXML can't be loaded
     */
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

    /**
     * Navigates the user to the "Create Account" view.
     * 
     * @throws IOException if the FXML can't be loaded
     */
    private void goToCreateAccount() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create Account");
    }

    /**
     * Displays an informational alert dialog to the user.
     * 
     * @param title the title of the alert dialog
     * @param message the message of the content of the alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

