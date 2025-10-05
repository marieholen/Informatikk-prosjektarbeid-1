package ui;

import core.User;
import persistency.UserPersistence;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.junit.jupiter.api.*;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest extends ApplicationTest {

    private LoginController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
        this.stage = stage;
    }

    @BeforeEach
    void backupAndClearUserFile() throws Exception {
        Path original = Path.of("../../data/users.json");
        Path backup = Path.of("../../data/users.json.bak");

        if (Files.exists(original)) {
            Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
        } else {
            if (original.getParent() != null && !Files.exists(original.getParent())) {
                Files.createDirectories(original.getParent());
            }
            Files.writeString(original, "[]", StandardOpenOption.CREATE);
        }
        Files.writeString(original, "[]", StandardOpenOption.TRUNCATE_EXISTING);
    }

    @AfterEach
    void restoreUserFile() throws Exception {
        Path original = Path.of("../../data/users.json");
        Path backup = Path.of("../../data/users.json.bak");

        if (Files.exists(backup)) {
            Files.move(backup, original, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Test
    void testAuthenticateUser() throws Exception {
        User testUser = new User("testuser", "password");
        UserPersistence.saveUsers(Collections.singletonList(testUser));

        assertNotNull(controller.authenticateUser("testuser", "password"), "Should find user with correct credentials");
        assertNull(controller.authenticateUser("testuser", "wrongpassword"), "Should fail with wrong password");
        assertNull(controller.authenticateUser("nonexistingUser", "password"), "Should fail for non-existing user");
    }

    @Test
    void testAuthenticateUserException() throws Exception {
        Path original = Path.of("../../data/users.json");
        Files.writeString(original, "{ not json }", StandardOpenOption.TRUNCATE_EXISTING);

        final User[] result = new User[1];
        Platform.runLater(() -> {
            result[0] = controller.authenticateUser("testuser", "password");
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertNull(result[0], "Should return null if user cannot be loaded");
    }

    @Test
    void testHandleLogin() throws Exception {
        User testUser = new User("testuser", "password");
        UserPersistence.saveUsers(Collections.singletonList(testUser));

        controller.usernameField.setText("testuser");
        controller.passwordField.setText("password");

        Platform.runLater(() -> controller.handleLogin(null));

        Thread.sleep(200);
        FxAssert.verifyThat("#btnLogout", NodeMatchers.isVisible());
    }

    @Test
    void testHandleInvalidUser() throws Exception {
        controller.usernameField.setText("invalidUser");
        controller.passwordField.setText("password");

        Platform.runLater(() -> controller.handleLogin(null));

        Thread.sleep(200);
    }

    @Test
    void testGoToMyRecipesAfterLogin() throws Exception {
        User testUser = new User("testuser", "password");
        UserPersistence.saveUsers(Collections.singletonList(testUser));

        clickOn("#usernameField").write("testuser");
        clickOn("#passwordField").write("password");
        clickOn("#loginButton");

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(stage.getScene().getRoot(), "Root should not be null after navigation");
    }

    @Test
    void testGoToMYRecipes() throws Exception {
        WaitForAsyncUtils.asyncFx(() -> {
            Stage stage = new Stage();
            stage.setScene(new Scene(new AnchorPane()));
            stage.show();

            try {
                controller.goToMyRecipes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testGoToCreateAccount() throws Exception {
        clickOn("#createAccountLink");

        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat("#signupButton", NodeMatchers.isVisible());
    }

}
