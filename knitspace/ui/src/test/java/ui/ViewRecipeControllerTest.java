package ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Recipe;
import core.enums.SkillLevel;
import core.enums.ProjectStatus;
import core.enums.YarnType;
import core.enums.NeedleSize;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import persistency.RecipePersistence;

public class ViewRecipeControllerTest extends ApplicationTest {
    private ViewRecipeController controller;
    private Parent root;
    private static final Path RECIPE_PATH = Path.of("../../data/recipes.json");
    private static final Path RECIPE_BACKUP_PATH = Path.of("../../data/recipes.backup.json");

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/ui/ViewRecipe.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @BeforeAll
    static void backupRecipeFile() throws IOException {
        if (Files.exists(RECIPE_PATH)) {
            Files.copy(RECIPE_PATH, RECIPE_BACKUP_PATH, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterAll
    static void restoreRealRecipeFile() throws Exception {
        if (Files.exists(RECIPE_BACKUP_PATH)) {
            Files.copy(RECIPE_BACKUP_PATH, RECIPE_PATH, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(RECIPE_BACKUP_PATH);
        }
    }

    @Test
    void testPageLoadsRecipeCorrectly() {
        Recipe testRecipe = new Recipe(UUID.randomUUID(),
         "My Sweater",
         "My favourite sweater",
         SkillLevel.BEGINNER,
         YarnType.COTTON,
         NeedleSize.SIZE_5_0,
         ProjectStatus.IN_PROGRESS
        );

        interact(() -> controller.setAndLoadRecipe(testRecipe));
        WaitForAsyncUtils.waitForFxEvents();

        Label title = lookup("#titleLabel").queryAs(Label.class);
        Label desc = lookup("#descriptionLabel").queryAs(Label.class);
        Label skill = lookup("#skillLevelLabel").queryAs(Label.class);

        assertEquals("My Sweater", title.getText());
        assertEquals("My favourite sweater", desc.getText());
        assertEquals(SkillLevel.BEGINNER.getDescription(), skill.getText());
    }

    @Test
    void testBackToRecipesLoadsMyRecipes() throws Exception {
        // Arrange
        Recipe testRecipe = new Recipe(UUID.randomUUID(),
            "Any",
            "Any",
            SkillLevel.EASY,
            YarnType.WOOL,
            NeedleSize.SIZE_4_0,
            ProjectStatus.PLANNED
        );
        interact(() -> controller.setAndLoadRecipe(testRecipe));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#backButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("#myRecipesLabel").query());
    }

    @Test
    void testDeleteRecipeShowsConfirmationAndCancel() throws Exception {
        Recipe testRecipe = new Recipe(UUID.randomUUID(),
            "To Delete",
            "Desc",
            SkillLevel.INTERMEDIATE,
            YarnType.ALPACA,
            NeedleSize.SIZE_5_0,
            ProjectStatus.IN_PROGRESS
        );
        interact(() -> controller.setAndLoadRecipe(testRecipe));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#deleteButton");
        clickOn("Cancel");

        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("#titleLabel").query());
    }

    @Test
    void testDeletedRecipeIsDeleted() throws Exception {
        List<Recipe> recipesBefore = RecipePersistence.loadRecipes();
        Recipe recipeToDelete = recipesBefore.get(0);

        UUID recipeToDeleteUUID = recipeToDelete.getRecipeId();

        interact(() -> controller.setAndLoadRecipe(recipeToDelete));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#deleteButton");
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("OK");
        WaitForAsyncUtils.waitForFxEvents();

        List<Recipe> recipesAfter = RecipePersistence.loadRecipes();

        assertTrue(
            recipesAfter.stream().noneMatch(
                recipe -> recipe.getRecipeId().equals(recipeToDeleteUUID)
            )
        );
    }

    @Test
    void testEditButtonLoadsEditRecipes() throws Exception {
        Recipe testRecipe = new Recipe(UUID.randomUUID(),
            "Edit",
            "Desc",
            SkillLevel.ADVANCED,
            YarnType.MERINO,
            NeedleSize.SIZE_4_5,
            ProjectStatus.PAUSED
        );
        interact(() -> controller.setAndLoadRecipe(testRecipe));
        WaitForAsyncUtils.waitForFxEvents();

        clickOn("#editButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(lookup("#formGrid").query());
    }
}

