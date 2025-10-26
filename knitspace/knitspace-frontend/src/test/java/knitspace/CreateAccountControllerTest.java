package knitspace;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import knitspace.client.UserClient;
import knitspace.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({ MockitoExtension.class, ApplicationExtension.class })
class CreateAccountControllerTest {

    @Mock
    private UserClient mockUserClient;

    private CreateAccountController controller;

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/CreateAccount.fxml"));
        Scene scene = new Scene(loader.load());

        controller = loader.getController();

        java.lang.reflect.Field userClientField = CreateAccountController.class.getDeclaredField("userClient");
        userClientField.setAccessible(true);
        userClientField.set(controller, mockUserClient);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testSignupButton_ValidInput_CallsUserClient(FxRobot robot) throws Exception {
        when(mockUserClient.createAccount(anyString(), anyString())).thenReturn(null);

        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#confirmPasswordField").write("password123");
        robot.clickOn("#signupButton");

        verify(mockUserClient).createAccount("testuser", "password123");
    }

    @Test
    void testSignupButton_EmptyUsername_ShowsErrorDialog(FxRobot robot) {
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#confirmPasswordField").write("password123");
        robot.clickOn("#signupButton");

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Username must be filled in.");
        });
    }

    @Test
    void testSignupButton_EmptyPassword_ShowsErrorDialog(FxRobot robot) {
        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#confirmPasswordField").write("password123");
        robot.clickOn("#signupButton");

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Password must be filled in.");
        });
    }

    @Test
    void testSignupButton_ShortPassword_ShowsErrorDialog(FxRobot robot) {
        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#passwordField").write("12345");
        robot.clickOn("#confirmPasswordField").write("12345");
        robot.clickOn("#signupButton");

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Password must be at least 6 characters.");
        });
    }

    @Test
    void testSignupButton_PasswordMismatch_ShowsErrorDialog(FxRobot robot) {
        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#confirmPasswordField").write("password456");
        robot.clickOn("#signupButton");

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("The passwords do not match.");
        });
    }

    @Test
    void testSignupButton_UserAlreadyExists_ShowsErrorDialog(FxRobot robot) throws Exception {
        when(mockUserClient.createAccount(anyString(), anyString()))
                .thenThrow(new ApiException("User already exists", 409));

        robot.clickOn("#usernameField").write("existinguser");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#confirmPasswordField").write("password123");
        robot.clickOn("#signupButton");

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Username already exists. Please choose a different username.");
        });
    }

    @Test
    void testLoginLink_NavigatesToLogin(FxRobot robot) {
        assertDoesNotThrow(() -> robot.lookup("#signupButton").query(), 
            "Should start on create account page");
        
        robot.clickOn("#loginLink");

        assertThrows(org.testfx.service.query.EmptyNodeQueryException.class, 
            () -> robot.lookup("#signupButton").query(), 
            "Signup button should not be present after navigation");
        
        assertDoesNotThrow(() -> robot.lookup("#loginButton").query(), 
            "Login page should have login button");
        assertDoesNotThrow(() -> robot.lookup("#createAccountLink").query(), 
            "Login page should have create account link");
        assertDoesNotThrow(() -> robot.lookup("#appTitleLabel").query(), 
            "Login page should have app title label");
    }
}