package knitspace;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import knitspace.client.UserClient;
import knitspace.exception.ApiException;
import knitspace.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.util.Objects;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;

import javafx.application.Platform;

@ExtendWith({ MockitoExtension.class, ApplicationExtension.class })
class LoginControllerTest {

    @Mock
    private UserClient mockUserClient;

    private LoginController controller;

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("/knitspace/Login.fxml"))
        );
        Scene scene = new Scene(loader.load());
    
        controller = loader.getController();
        var userClientField = LoginController.class.getDeclaredField("userClient");
        userClientField.setAccessible(true);
        userClientField.set(controller, mockUserClient);
    
        stage.setScene(scene);
        stage.setTitle("Login"); 
        stage.show();

        Platform.runLater(() -> {
            stage.toFront();
            stage.requestFocus();
        });

        waitForFxEvents();
    }

    @Test
    void testLoginButton_ValidInput_CallsUserClient(FxRobot robot) throws Exception {
        User mockUser = new User("1", "testuser", null);
        when(mockUserClient.loginAsync(anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(mockUser));

        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#loginButton");

        waitForFxEvents();

        verify(mockUserClient).loginAsync("testuser", "password123");
    }

    @Test
    void testLoginButton_EmptyUsername_ShowsErrorDialog(FxRobot robot) throws Exception {
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#loginButton");

        waitForFxEvents();

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Please enter both username and password.");
        });
    }

    @Test
    void testLoginButton_EmptyPassword_ShowsErrorDialog(FxRobot robot) throws Exception {
        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#loginButton");

        waitForFxEvents();

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Please enter both username and password.");
        });
    }

    @Test
    void testLoginButton_BothFieldsEmpty_ShowsErrorDialog(FxRobot robot) throws Exception {
        robot.clickOn("#loginButton");

        waitForFxEvents();

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Please enter both username and password.");
        });
    }

    @Test
    void testLoginButton_InvalidCredentials_ShowsErrorDialog(FxRobot robot) throws Exception {
        CompletableFuture<User> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(new ApiException("Invalid username or password", 401));
        
        when(mockUserClient.loginAsync(anyString(), anyString()))
                .thenReturn(failedFuture);

        robot.clickOn("#usernameField").write("wronguser");
        robot.clickOn("#passwordField").write("wrongpass");
        robot.clickOn("#loginButton");

        waitForFxEvents();

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Invalid username or password");
        });
    }

    @Test
    void testLoginButton_ApiError_ShowsErrorDialog(FxRobot robot) throws Exception {
        CompletableFuture<User> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(new ApiException("Server error", 500));
        
        when(mockUserClient.loginAsync(anyString(), anyString()))
                .thenReturn(failedFuture);

        robot.clickOn("#usernameField").write("testuser");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#loginButton");

        waitForFxEvents();

        verifyThat(".dialog-pane", node -> {
            DialogPane dialogPane = (DialogPane) node;
            return dialogPane.getContentText().contains("Server error");
        });
    }
}

