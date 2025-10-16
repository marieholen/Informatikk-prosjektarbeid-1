package ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import persistency.UserPersistence;
import core.SessionManager;
import core.User;

/**
 * Controller class responsible for handling user interactions on the 
 * "Create account" page. 
 * <p>
 * The controller includes input validation, user creation, 
 * and navagation between the sign-up and log-in scenes in the application. 
 * The class interacts with {@link UserPersitence} for user storage and 
 * {@link SessionManager} to set the current logged-in user. 
 * </p>
 */
public class CreateAccountController {

    /** Button used to trigger the sign-up process. */
    @FXML Button signupButton;

    /** Text field where the user enters their desired username. */
    @FXML private TextField usernameField;

    /** Password field for entering and confirming the user´s password. */
    @FXML private PasswordField passwordField, confirmPasswordField;

    /** Hyperlink that navigates the user to the login page */
    @FXML Hyperlink loginLink;



    /**
     * Initializes the controller by setting event handlers for the sign-up
     * button and the login hyperlink. This methos is autmatically called after
     * the FXML file has been loaded. 
     */
    @FXML
    private void initialize() {
        signupButton.setOnAction(e -> onSignup());
        loginLink.setOnAction(e -> onLogIn());
    }

    /**
     * Handles navigation to the login page when the login link is clicked. 
     */
    private void onLogIn() {
        goToLogin(loginLink);
    }

    /**
     * Handles user registration when the sign-up button is clicked. 
     * <p>
     * Manages validation on input fields, checks for existing usernames, 
     * creates a new {@link User}, saves it via {@link UserPersistence}, 
     * and then navigates to the "MyRecipes" page. 
     * </p>
     */
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

            setSelectedUser(newUser);

            showInfoPopup("User created", "The user was created. You will now be logged in.");
            goToMyRecipes(signupButton);

        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("System error", "Something went wrong while saving. Please try again.");
        }

    }


    /**
     * Validates the user´s input during sign-up. 
     * 
     * @param username the entered username
     * @param password the entered password
     * @param confirmPassword the repeated password
     * @return an {@link Optional} containing an error message if invalid, 
     *         or {@link Optional#empty()} if the input is valid
     */
    Optional<String> validateInput(String username, String password, String confirmPassword) {
        if (username == null || username.isEmpty())
            return Optional.of("Username must be filled in.");
        if (password == null || password.isEmpty())
            return Optional.of("Password must be filled in.");
        if (confirmPassword == null || confirmPassword.isEmpty())
            return Optional.of("Must confirm password.");
        if (password.length() < 6)
            return Optional.of("Password must be at least 6 characters.");
        if (!password.equals(confirmPassword))
            return Optional.of("The passwords do not match.");
        return Optional.empty();
    }


    /**
     * Displays an error popup dialog with a given title and message. 
     * 
     * @param title the title of the popup
     * @param message the error message to display
     */
    void showErrorPopup(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Displays an information popup dialog with a given title and message. 
     * 
     * @param title the title of the popup
     * @param message the information message to display
     */
    void showInfoPopup(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Navigates the user to "MyRecipes" page
     * 
     * @param anyNodeInCurrentScene a reference to any node in the current scene, 
     *                              used to obtain the current {@link Stage}
     */
    void goToMyRecipes(Node anyNodeInCurrentScene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MyRecipes.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) anyNodeInCurrentScene.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("Navigation error", "Could not open the MyRecipes page.");
        }
    }


    /**
     * Navigates the user to the login page. 
     * 
     * @param anyNodeInCurrentScene a reference to any node in the current scene, 
     *                              used to obtain the current {@link Stage}
     */
    private void goToLogin(Node anyNodeInCurrentScene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) anyNodeInCurrentScene.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("Navigation error", "Could not open the Login page.");
        }
    }

    /**
     * Sets the specified user as the currently logged-in user in the session. 
     * 
     * @param newUser the {@link User} to set as current session user
     */
    private void setSelectedUser(User newUser) {
        SessionManager.setCurrentUser(newUser);
    }

}
