package knitspace;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupStage;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class ViewRecipeControllerTest extends ApplicationTest {
    private Stage stage;
    private ViewRecipeController controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/ViewRecipe.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setScene(new Scene(loader.load()));
        stage.show();

        controller = loader.getController();
    }

    @Test
    public void testEditButtonIsVisible() {
        verifyThat("#editButton", isVisible());
    }

    @Test
    public void testClickonEditButton() {
        clickOn("#editButton");
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/EditRecipeView.fxml"));
        Scene scene = new Scene(loader.load());

        Platform.runLater(() -> stage.setScene(scene));
        sleep(500);
    
        verifyThat("#saveButton", isVisible());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShowAlertOnError() {
        Platform.runLater(() -> {
            try {
                ViewRecipeController controller = new ViewRecipeController();
                controller.goToEditRecipe(null, "ViewRecipe");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sleep(500);
        verifyThat(".alert", isVisible());
    }
}
