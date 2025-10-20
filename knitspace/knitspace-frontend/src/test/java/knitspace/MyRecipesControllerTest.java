package knitspace;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MyRecipesControllerTest {

    private Stage stage;

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));
        Scene scene = new Scene(loader.load());
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testNewRecipeButtonRedirectsToEditRecipeView(FxRobot robot) {
        robot.clickOn("#btnNewrecipe");

        assertEquals("Create New Recipe", stage.getTitle(),
                "Stage title should change to 'Create New Recipe' after clicking New Recipe button");

        assertNotNull(robot.lookup("#saveButton").query(),
                "EditRecipeView.fxml should be loaded and contain a Save button");
    }
}
