package ui;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistency.UserPersistence;

public class CreateAccountControllerTest extends ApplicationTest {
    private CreateAccountController controller;
    private Parent root;
    private Stage stage;
    private static final Path USER_PATH = Path.of("../../data/users.json");
    private static final Path BACKU_PATH = Path.of("../../data/users.json.bakcup_for_tests");

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("CreateAccount.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeAll
    static void backupReakUserFile() throws Exception {
        if (Files.exists(USER_PATH)) {
            if (Files.exists(BACKU_PATH)) {
                Files.delete(BACKU_PATH);
            }
            Files.copy(USER_PATH, BACKU_PATH, StandardCopyOption.REPLACE_EXISTING);
            Files.writeString(USER_PATH, "[]", StandardOpenOption.TRUNCATE_EXISTING);
        } else {
            if (USER_PATH.getParent() != null && !Files.exists(USER_PATH.getParent())) {
                Files.createDirectories(USER_PATH.getParent());
            }
            Files.writeString(USER_PATH, "[]", StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @AfterAll
    static void restoreRealUserFile() throws Exception {
        if (Files.exists(BACKU_PATH)) {
            Files.move(BACKU_PATH, USER_PATH, StandardCopyOption.REPLACE_EXISTING);
        } else {
            if (Files.exists(USER_PATH)) {
                Files.delete(USER_PATH);
            }
        }
    }

    @BeforeEach
    void clearUsersFileBeforeTest() throws Exception {
        Files.writeString(USER_PATH, "[]", StandardOpenOption.TRUNCATE_EXISTING);
    }

    @BeforeEach
    void setupStage() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @AfterEach
    void cleanupStage() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    void testValidateInput() throws Exception {
        Optional<String> error1 = controller.validateInput("", "password", "password");
        assertTrue(error1.isPresent(), "Should return error for empty username");

        Optional<String> error2 = controller.validateInput("username", "", "password");
        assertTrue(error2.isPresent(), "Should return error for empty password");

        Optional<String> error3 = controller.validateInput("username", "password", "password2");
        assertTrue(error3.isPresent(), "Should return error for mismatched passwords");

        Optional<String> error4 = controller.validateInput("username", "pass", "pass");
        assertTrue(error4.isPresent(), "Should return error for too short password");

        Optional<String> error5 = controller.validateInput(null, "password", "password");
        assertTrue(error5.isPresent(), "Should return error null input");

        Optional<String> ok = controller.validateInput("username", "password", "password");
        assertFalse(ok.isPresent(), "Should return empty Optional for valid input");

    }

    @Test
    void testExistingUserShowsError() throws Exception {

        UserPersistence.addUser(new User("existingUser", "password"));

        clickOn("#usernameField").write("existingUser");
        clickOn("#passwordField").write("password");
        clickOn("#confirmPasswordField").write("password");
        clickOn("#signupButton");

        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat(".alert", NodeMatchers.isVisible());
    }

    @Test
    void testShowInfoPopup() throws Exception {

        WaitForAsyncUtils.asyncFx(() -> {
            controller.showInfoPopup("Info", "This is an info popup");
        });

        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat(".alert", NodeMatchers.isVisible());

    }

    @Test
    void testShowErrorPopup() throws Exception {

        WaitForAsyncUtils.asyncFx(() -> {
            controller.showErrorPopup("Error", "This is an error popup");
        });

        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat(".alert", NodeMatchers.isVisible());

    }

    @Test
    void testGoToLogin() throws Exception {

        clickOn("#loginLink");

        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat("#usernameField", NodeMatchers.isVisible());
    }

    @Test
    void testGoToMyRecipiesAfterSignup() throws Exception {

        clickOn("#usernameField").write("testuser");
        clickOn("#passwordField").write("password");
        clickOn("#confirmPasswordField").write("password");
        clickOn("#signupButton");

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(stage.getScene().getRoot(), "Root should not be null after navigation");

    }

    @Test
    void testGoToMYRecipes() throws Exception {
        WaitForAsyncUtils.asyncFx(() -> {
            Stage stage = new Stage();
            stage.setScene(new Scene(new AnchorPane()));
            stage.show();

            controller.goToMyRecipes(stage.getScene().getRoot());
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testInitialize() {
        assertNotNull(controller.signupButton.getOnAction(), "Signup button should have action handler");
        assertNotNull(controller.loginLink.getOnAction(), "Login link should have action handler");
    }

}
