package knitspace;

import javafx.scene.control.ComboBox;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.*;
import javafx.application.Platform;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class EditRecipeControllerTest {

    private EditRecipeController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new EditRecipeController();
        
        Field difficultyField = EditRecipeController.class.getDeclaredField("difficultyComboBox");
        difficultyField.setAccessible(true);
        difficultyField.set(controller, new ComboBox<>());

        Field yarnTypeField = EditRecipeController.class.getDeclaredField("yarnTypeComboBox");
        yarnTypeField.setAccessible(true);
        yarnTypeField.set(controller, new ComboBox<>());

        Field needleSizeField = EditRecipeController.class.getDeclaredField("needleSizeComboBox");
        needleSizeField.setAccessible(true);
        needleSizeField.set(controller, new ComboBox<>());

        Field statusField = EditRecipeController.class.getDeclaredField("statusComboBox");
        statusField.setAccessible(true);
        statusField.set(controller, new ComboBox<>());
    }

    @Test
    void testDifficultyComboBoxInitialization() throws Exception {
        Method method = EditRecipeController.class.getDeclaredMethod("initializeDifficultyComboBox");
        method.setAccessible(true);
        method.invoke(controller);

        Field field = EditRecipeController.class.getDeclaredField("difficultyComboBox");
        field.setAccessible(true);
        ComboBox<String> comboBox = (ComboBox<String>) field.get(controller);

        assertEquals(5, comboBox.getItems().size());
        assertTrue(comboBox.getItems().contains("Beginner"));
        assertTrue(comboBox.getItems().contains("Expert"));
    }

    @Test
    void testYarnTypeComboBoxInitialization() throws Exception {
        Method method = EditRecipeController.class.getDeclaredMethod("initializeYarnTypeComboBox");
        method.setAccessible(true);
        method.invoke(controller);

        Field field = EditRecipeController.class.getDeclaredField("yarnTypeComboBox");
        field.setAccessible(true);
        ComboBox<String> comboBox = (ComboBox<String>) field.get(controller);

        assertTrue(comboBox.getItems().contains("Wool"));
        assertTrue(comboBox.getItems().contains("Cotton"));
    }

    @Test
    void testInitializePopulatesAllComboBoxes() throws Exception {
        Method initMethod = EditRecipeController.class.getDeclaredMethod("initialize");
        initMethod.setAccessible(true);
        initMethod.invoke(controller);

        Field difficultyField = EditRecipeController.class.getDeclaredField("difficultyComboBox");
        difficultyField.setAccessible(true);
        ComboBox<String> difficultyBox = (ComboBox<String>) difficultyField.get(controller);

        Field yarnField = EditRecipeController.class.getDeclaredField("yarnTypeComboBox");
        yarnField.setAccessible(true);
        ComboBox<String> yarnBox = (ComboBox<String>) yarnField.get(controller);

        assertFalse(difficultyBox.getItems().isEmpty());
        assertFalse(yarnBox.getItems().isEmpty());
    }

    @Test
    void testHandleBackButtonSuccessPath() throws Exception {
    try (MockedConstruction<FXMLLoader> mocked = mockConstruction(
            FXMLLoader.class,
            (mock, context) -> when(mock.load()).thenReturn(new Pane()))) {

        Platform.runLater(() -> {
            try {
                Button backButton = new Button("Back");
                Stage stage = new Stage();
                Pane root = new Pane(backButton);
                Scene scene = new Scene(root);
                stage.setScene(scene);

                Field buttonField = EditRecipeController.class.getDeclaredField("backButton");
                buttonField.setAccessible(true);
                buttonField.set(controller, backButton);

                controller.handleBackButton(new ActionEvent());

                assertEquals("My Recipes", stage.getTitle());

            } catch (Exception e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        });

        org.testfx.util.WaitForAsyncUtils.waitForFxEvents();
    }
    }



    @Test
    void testHandleBackButtonCatchPath() {
        assertDoesNotThrow(() ->
            controller.handleBackButton(new ActionEvent())
        );
    }
}