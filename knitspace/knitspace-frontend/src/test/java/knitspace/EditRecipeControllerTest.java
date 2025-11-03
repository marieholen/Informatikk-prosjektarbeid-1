package knitspace;

import javafx.scene.control.ComboBox;
import knitspace.client.RecipeClient;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.enums.NeedleSize;
import knitspace.exception.ApiException;
import knitspace.model.Recipe;
import knitspace.model.User;
import knitspace.meta.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import org.mockito.MockedConstruction;
import javafx.application.Platform;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;

@ExtendWith(ApplicationExtension.class)
class EditRecipeControllerTest {

    @Mock
    private RecipeClient recipeClient;

    private EditRecipeController controller;
    private Field titleField;
    private Field descriptionField;
    private Field difficultyField;
    private Field yarnTypeField;
    private Field needleSizeField;
    private Field statusField; 

    @BeforeEach
    void setUp() throws Exception {
        recipeClient = mock(RecipeClient.class);
        controller = spy(new EditRecipeController(recipeClient));

        titleField = EditRecipeController.class.getDeclaredField("titleField");
        titleField.setAccessible(true);
        titleField.set(controller, new TextField());

        descriptionField = EditRecipeController.class.getDeclaredField("descriptionField");
        descriptionField.setAccessible(true);
        descriptionField.set(controller, new TextField());
        
        difficultyField = EditRecipeController.class.getDeclaredField("difficultyComboBox");
        difficultyField.setAccessible(true);
        difficultyField.set(controller, new ComboBox<>());

        yarnTypeField = EditRecipeController.class.getDeclaredField("yarnTypeComboBox");
        yarnTypeField.setAccessible(true);
        yarnTypeField.set(controller, new ComboBox<>());

        needleSizeField = EditRecipeController.class.getDeclaredField("needleSizeComboBox");
        needleSizeField.setAccessible(true);
        needleSizeField.set(controller, new ComboBox<>());

        statusField = EditRecipeController.class.getDeclaredField("statusComboBox");
        statusField.setAccessible(true);
        statusField.set(controller, new ComboBox<>());

        User mockUser = new User("testID", "testUsername", "testPassword");
        SessionManager.setCurrentUser(mockUser);

        doNothing().when(controller).showAlert(anyString(), anyString());
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
    void testLoadFromViewRecipePopulatesFieldsCorrectly() throws Exception {
        Recipe testRecipe = new Recipe(
            "testRecipeId",
            "testUserId",
            "Test Recipe Title",
            "Test Recipe Description",
            SkillLevel.BEGINNER,
            YarnType.COTTON,
            NeedleSize.SIZE_2_0,
            ProjectStatus.ABANDONED
        );

        controller.loadFromViewRecipe(testRecipe);

        assertEquals("Test Recipe Title", ((TextField) titleField.get(controller)).getText());
        
        assertEquals("Test Recipe Description", ((TextField) descriptionField.get(controller)).getText());
        
        assertEquals("Beginner", ((ComboBox<String>) difficultyField.get(controller)).getValue());

        assertEquals("Cotton", ((ComboBox<String>) yarnTypeField.get(controller)).getValue());

        assertEquals("2.0 mm", ((ComboBox<String>) needleSizeField.get(controller)).getValue());

        assertEquals("Abandoned - unraveled and abandoned (for now...)", ((ComboBox<String>) statusField.get(controller)).getValue());
    }

    @Test
    void testLoadFromMyRecipesClearsAllFields() throws Exception {
        controller.loadFromMyRecipes();
        
        assertEquals("", ((TextField) titleField.get(controller)).getText());

        assertEquals("", ((TextField) descriptionField.get(controller)).getText());
        
        assertEquals(null, ((ComboBox<String>) difficultyField.get(controller)).getValue());

        assertEquals(null, ((ComboBox<String>) yarnTypeField.get(controller)).getValue());

        assertEquals(null, ((ComboBox<String>) needleSizeField.get(controller)).getValue());

        assertEquals(null, ((ComboBox<String>) statusField.get(controller)).getValue());
    }

    @Test
    void testCheckFormInputOkWhenAllFieldsValidReturnsTrue() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("This is a valid title!:-)");
        ((TextField) descriptionField.get(controller)).setText("This is a valid description!:-)");
        ((ComboBox<String>) difficultyField.get(controller)).setValue("Beginner");
        ((ComboBox<String>) yarnTypeField.get(controller)).setValue("Cotton");
        ((ComboBox<String>) needleSizeField.get(controller)).setValue("2.0 mm");
        ((ComboBox<String>) statusField.get(controller)).setValue("In Progress");

        assertTrue(controller.checkFormInputOk());
    }

    @Test
    void testIsValidTitle() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("This is a valid title!:-)");
        assertTrue(controller.isValidTitle());
        
        ((TextField) titleField.get(controller)).setText("");
        assertFalse(controller.isValidTitle());

        ((TextField) titleField.get(controller)).setText(" ");
        assertFalse(controller.isValidTitle());
        
        ((TextField) titleField.get(controller)).setText(null);
        assertFalse(controller.isValidTitle());
    }

    @Test
    void testIsValidDescription() throws IllegalAccessException {
        ((TextField) descriptionField.get(controller)).setText("This is a valid description!:-)");
        assertTrue(controller.isValidDescription());
        
        ((TextField) descriptionField.get(controller)).setText("");
        assertFalse(controller.isValidDescription());

        ((TextField) descriptionField.get(controller)).setText(" ");
        assertFalse(controller.isValidDescription());
        
        ((TextField) descriptionField.get(controller)).setText(null);
        assertFalse(controller.isValidDescription());
    }

    @Test
    void testGetValidationErrorMessageWithEmptyTitle() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("");
        
        assertEquals("Title cannot be empty or null!", controller.getValidationErrorMessage());
    }

    @Test
    void testGetValidationErrorMessageWithEmptyDescription() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("This is a valid title!:-)");
        ((TextField) descriptionField.get(controller)).setText("");
        
        assertEquals("Description cannot be empty or null!!", controller.getValidationErrorMessage());
    }

    @Test
    void testGetValidationErrorMessageWithNoDifficulty() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("This is a valid title!:-)");
        ((TextField) descriptionField.get(controller)).setText("This is a valid description!:-)");
        ((ComboBox<String>) difficultyField.get(controller)).setValue(null);
        
        assertEquals("Please select a difficulty level.", controller.getValidationErrorMessage());
    }

    @Test
    void testGetValidationErrorMessageWithNoYarnType() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("This is a valid title!:-)");
        ((TextField) descriptionField.get(controller)).setText("This is a valid description!:-)");
        ((ComboBox<String>) difficultyField.get(controller)).setValue("Beginner");
        
        assertEquals("Please select a yarn type.", controller.getValidationErrorMessage());
    }

    @Test
    void testGetValidationErrorMessageWithNoNeedleSize() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("This is a valid title!:-)");
        ((TextField) descriptionField.get(controller)).setText("This is a valid description!:-)");
        ((ComboBox<String>) difficultyField.get(controller)).setValue("Beginner");
        ((ComboBox<String>) yarnTypeField.get(controller)).setValue("Cotton");
        
        assertEquals("Please select a needle size.", controller.getValidationErrorMessage());
    }

    @Test
    void testGetValidationErrorMessageWithNoStatus() throws IllegalAccessException {
        ((TextField) titleField.get(controller)).setText("This is a valid title!:-)");
        ((TextField) descriptionField.get(controller)).setText("This is a valid description!:-)");
        ((ComboBox<String>) difficultyField.get(controller)).setValue("Beginner");
        ((ComboBox<String>) yarnTypeField.get(controller)).setValue("Cotton");
        ((ComboBox<String>) needleSizeField.get(controller)).setValue("2.0 mm");
        
        assertEquals("Please select a project status.", controller.getValidationErrorMessage());
    }

    @Test
    void testHandleSaveButtonCreateNewRecipeSucess() throws Exception {
        controller.loadFromMyRecipes();

        ((TextField) titleField.get(controller)).setText("Test Recipe Title");
        ((TextField) descriptionField.get(controller)).setText("Test Recipe Description");
        ((ComboBox<String>) difficultyField.get(controller)).setValue("Beginner");
        ((ComboBox<String>) yarnTypeField.get(controller)).setValue("Cotton");
        ((ComboBox<String>) needleSizeField.get(controller)).setValue("2.0 mm");
        ((ComboBox<String>) statusField.get(controller)).setValue("In Progress");

        Method handleSaveButton = EditRecipeController.class.getDeclaredMethod("handleSaveButton", ActionEvent.class);
        handleSaveButton.setAccessible(true);
        ActionEvent mockEvent = mock(ActionEvent.class);
        handleSaveButton.invoke(controller, mockEvent);

        verify(recipeClient).createRecipe(any(Recipe.class));
    }

    @Test
    void testHandleSaveButtonUpdateRecipeSuccess() throws Exception {
        Recipe testRecipe = new Recipe(
            "testRecipeId",
            "testUserId",
            "Test Recipe Title",
            "Test Recipe Description",
            SkillLevel.BEGINNER,
            YarnType.COTTON,
            NeedleSize.SIZE_2_0,
            ProjectStatus.ABANDONED
        );

        controller.loadFromViewRecipe(testRecipe);
        Method handleSaveButton = EditRecipeController.class.getDeclaredMethod("handleSaveButton", ActionEvent.class);
        handleSaveButton.setAccessible(true);
        ActionEvent mockEvent = mock(ActionEvent.class);
        handleSaveButton.invoke(controller, mockEvent);

        verify(recipeClient).updateRecipe(any(Recipe.class));
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