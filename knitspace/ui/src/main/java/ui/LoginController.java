package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import core.User;
import core.SessionManager;
import persistency.UserPersistence;

import java.io.IOException;
import java.util.List;

/**
 * Controller class responsible for managing the login functionality 
 * of the application. 
 * <p>
 * This controller handles user authentication, input validation and navigation
 * to ogher scenes ("CreateAccount" and "MyRecipes"). The class also interacts 
 * with {@link UserPersistence} for verifying user information and 
 * {@link SessionManager} for soring the active user session. 
 * </p>
 */
public class LoginController {

    /** Text field for entering the username */
    @FXML TextField usernameField; 

    /** Password field for entering the password */
    @FXML PasswordField passwordField;

    /** Button that triggers the login process */
    @FXML Button loginButton;

    /** Hyperlink that navigates to the "Create Account" page */
    @FXML Hyperlink createAccountLink;

    
    
    /**
     * Initiliazes the controller by setting up event handlers for the login 
     * button, text fields, and create account link. This method is automatically
     * called by the JavaFX framework after the FXML file is loaded. 
     */
    @FXML
    private void initialize() {
        loginButton.setOnAction(this::handleLogin);
        usernameField.setOnAction(this::handleLogin);
        passwordField.setOnAction(this::handleLogin);
        createAccountLink.setOnAction(this::handleCreateAccount);
    }

    /**
     * Handles the login button click. Validates user input, varifies credentials 
     * via {@link #authenticateUser(String, String)}. Displays an alert if login or
     * validation fails. 
     * 
     * @param event the {@link ActionEvent} that triggered this method
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
     * Authenticates the user by checking their username and password against the
     * stored user data. 
     * 
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return the autenticated {@link User} if valid credentials are found, 
     *         otherwhise {@code null}
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
     * Handles navigation to the "Create Account" page when the user clicks the
     * create account link. 
     * 
     * @param event the {@link ActionEvent} that triggered this method. 
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
     * Navigates the user to the "MyRecipes" page. 
     * 
     * @throws IOException if the FXML file for "MyRecipes" cannot be loaded
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
     * Navigates the user to the "Create Account" page. 
     * 
     * @throws IOException if the FXML file for "CreateAccount" cannot be loaded
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
     * Displays an information alert dialog with the given title and message. 
     * 
     * @param title the title of the alert window
     * @param message the message to display in the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

