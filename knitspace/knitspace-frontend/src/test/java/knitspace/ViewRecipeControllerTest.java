package knitspace;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Parent;

import knitspace.enums.*;
import knitspace.model.Recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.application.Platform;
import org.testfx.util.WaitForAsyncUtils;
import static org.mockito.Mockito.*;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@ExtendWith({MockitoExtension.class, ApplicationExtension.class})
public class ViewRecipeControllerTest {
   
    private ViewRecipeController controller;

    @Mock
    private Recipe mockRecipe;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/ViewRecipe.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setupMocks() {
        lenient().when(mockRecipe.title()).thenReturn("Test Title");
        lenient().when(mockRecipe.description()).thenReturn("Test Description");
        lenient().when(mockRecipe.skillLevel()).thenReturn(SkillLevel.BEGINNER);
        lenient().when(mockRecipe.yarnType()).thenReturn(YarnType.WOOL);
        lenient().when(mockRecipe.needleSize()).thenReturn(NeedleSize.SIZE_8_0);
        lenient().when(mockRecipe.projectStatus()).thenReturn(ProjectStatus.IN_PROGRESS);
    }

    @Test
    void testLabelsAreUpdatedWithRecipe(FxRobot robot) {
        Platform.runLater(() -> controller.setAndLoadRecipe(mockRecipe));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Test Title", controller.getTitleLabel().getText());
        assertEquals("Test Description", controller.getDescriptionLabel().getText());

        assertEquals("Beginner", controller.getSkillLevelLabel().getText());
        assertEquals("Wool", controller.getYarnLabel().getText());
        assertEquals("8.0 mm", controller.getNeedleLabel().getText());
        assertEquals("In Progress - currently knitting", controller.getStatusLabel().getText());
    }

    @Test
    void testHandleEditButtonClick(FxRobot robot) {
        Platform.runLater(() -> controller.setAndLoadRecipe(mockRecipe));
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#editButton");
        WaitForAsyncUtils.waitForFxEvents();
        
        verifyThat("#saveButton", isVisible());
    }

    @Test
    public void testShowAlertOnError(FxRobot robot) throws InterruptedException {
        controller.showAlert("Error", "This is a test alert");
        Thread.sleep(500);
        verifyThat(".dialog-pane", isVisible());
    }


}
