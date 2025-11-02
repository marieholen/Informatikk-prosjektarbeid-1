package knitspace;

import javafx.scene.control.ComboBox;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.enums.NeedleSize;
import knitspace.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javafx.scene.control.TextField;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class EditRecipeControllerTest {

    private EditRecipeController controller;
    private Field titleField;
    private Field descriptionField;
    private Field difficultyField;
    private Field yarnTypeField;
    private Field needleSizeField;
    private Field statusField; 

    @BeforeEach
    void setUp() throws Exception {
        controller = new EditRecipeController();

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

}