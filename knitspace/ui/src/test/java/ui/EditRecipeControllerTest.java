package ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.enums.SkillLevel;
import core.enums.YarnType;
import core.enums.NeedleSize;
import core.enums.ProjectStatus;
import core.Recipe;

import javafx.scene.control.ComboBox;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.UUID;

public class EditRecipeControllerTest {

    private EditRecipeController controller;

    @BeforeEach
    public void setUp() {
        controller = new EditRecipeController();
    }

    @Test
    public void testSetAndGetSelectedRecipe() {
        UUID userId = UUID.randomUUID();
        Recipe originalRecipe = new Recipe(
                userId,
                "Test Recipe",
                "Test Description",
                SkillLevel.INTERMEDIATE,
                YarnType.COTTON,
                NeedleSize.SIZE_4_5,
                ProjectStatus.IN_PROGRESS);

        controller.setSelectedRecipe(originalRecipe);
        Recipe retrievedRecipe = controller.getSelectedRecipe();

        assertNotSame(originalRecipe, retrievedRecipe);
        assertEquals(originalRecipe.getRecipeId(), retrievedRecipe.getRecipeId());
        assertEquals(originalRecipe.getUserId(), retrievedRecipe.getUserId());
        assertEquals(originalRecipe.getTitle(), retrievedRecipe.getTitle());
        assertEquals(originalRecipe.getDescription(), retrievedRecipe.getDescription());
        assertEquals(originalRecipe.getSkillLevel(), retrievedRecipe.getSkillLevel());
        assertEquals(originalRecipe.getYarnType(), retrievedRecipe.getYarnType());
        assertEquals(originalRecipe.getNeedleSize(), retrievedRecipe.getNeedleSize());
        assertEquals(originalRecipe.getProjectStatus(), retrievedRecipe.getProjectStatus());
    }

    @Test
    public void testGetSelectedSkillLevel() throws Exception {
        ComboBox<String> combo = new ComboBox<>();
        combo.setValue(SkillLevel.BEGINNER.getDescription());

        Field field = EditRecipeController.class.getDeclaredField("difficultyComboBox");
        field.setAccessible(true);
        field.set(controller, combo);

        Method method = EditRecipeController.class.getDeclaredMethod("getSelectedSkillLevel");
        method.setAccessible(true);
        SkillLevel result = (SkillLevel) method.invoke(controller);

        assertEquals(SkillLevel.BEGINNER, result);
    }

    @Test
    public void testGetSelectedYarnType() throws Exception {
        ComboBox<String> combo = new ComboBox<>();
        combo.setValue(YarnType.COTTON.getLabel());

        Field field = EditRecipeController.class.getDeclaredField("yarnTypeComboBox");
        field.setAccessible(true);
        field.set(controller, combo);

        Method method = EditRecipeController.class.getDeclaredMethod("getSelectedYarnType");
        method.setAccessible(true);
        YarnType result = (YarnType) method.invoke(controller);

        assertEquals(YarnType.COTTON, result);
    }

    @Test
    public void testGetSelectedNeedleSize() throws Exception {
        ComboBox<String> combo = new ComboBox<>();
        combo.setValue(NeedleSize.SIZE_4_5.getLabel());

        Field field = EditRecipeController.class.getDeclaredField("needleSizeComboBox");
        field.setAccessible(true);
        field.set(controller, combo);

        Method method = EditRecipeController.class.getDeclaredMethod("getSelectedNeedleSize");
        method.setAccessible(true);
        NeedleSize result = (NeedleSize) method.invoke(controller);

        assertEquals(NeedleSize.SIZE_4_5, result);
    }

    @Test
    public void testGetSelectedProjectStatus() throws Exception {
        ComboBox<String> combo = new ComboBox<>();
        combo.setValue(ProjectStatus.FINISHED.getDescription());

        Field field = EditRecipeController.class.getDeclaredField("statusComboBox");
        field.setAccessible(true);
        field.set(controller, combo);

        Method method = EditRecipeController.class.getDeclaredMethod("getSelectedProjectStatus");
        method.setAccessible(true);
        ProjectStatus result = (ProjectStatus) method.invoke(controller);

        assertEquals(ProjectStatus.FINISHED, result);
    }
}