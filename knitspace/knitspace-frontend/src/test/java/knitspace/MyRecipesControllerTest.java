package knitspace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.lang.reflect.Method;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import knitspace.client.RecipeClient;
import knitspace.meta.SessionManager;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.enums.NeedleSize;
import knitspace.model.Recipe;
import knitspace.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import javafx.scene.control.TextField;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class MyRecipesControllerTest {

    @Mock
    private RecipeClient recipeClient;

    private Stage stage;
    private MyRecipesController controller;

    @BeforeEach
    void setup() {
        SessionManager.setCurrentUser(null);
        controller = new MyRecipesController(recipeClient);
        controller.searchField = new TextField(); 
    }
    
    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));

        FxRobot robot = new FxRobot();
        Platform.runLater(() -> {
            robot.clickOn("OK");
        });

        Scene scene = new Scene(loader.load());
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void loadRecipes_UsesRecipeClientAndReturnsRecipes() throws Exception {
        User user = new User("user1", "tester", "password");
        SessionManager.setCurrentUser(user);

        Recipe r1 = new Recipe("r1", "user1", "Title1", "Desc1", null, null, null, null);
        Recipe r2 = new Recipe("r2", "user1", "Title2", "Desc2", null, null, null, null);

        List<Recipe> expected = List.of(r1, r2);

        when(recipeClient.getRecipesForUser(user)).thenReturn(expected);

        MyRecipesController controller = new MyRecipesController(recipeClient);

        Method m = MyRecipesController.class.getDeclaredMethod("loadRecipes");
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Recipe> actual = (List<Recipe>) m.invoke(controller);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void testNewRecipeButtonRedirectsToEditRecipeView(FxRobot robot) {
        robot.clickOn("#btnNewrecipe");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Create New Recipe", stage.getTitle(),
                "Stage title should change to 'Create New Recipe' after clicking New Recipe button");

        assertNotNull(robot.lookup("#saveButton").query(),
                "EditRecipeView.fxml should be loaded and contain a Save button");
    }

    @Test
    void testMatchesSearchField() {
    MyRecipesController controller = new MyRecipesController();
    controller.searchField = new TextField();
    Recipe recipe = new Recipe("recipeId", "userId", "Wool Sweater", "A warm sweater", null, null, null, null);

    controller.searchField.setText("");
    assertTrue(controller.matchesSearchField(recipe), "Blank search should match all recipes");

    controller.searchField.setText("wool");
    assertTrue(controller.matchesSearchField(recipe), "Matching text should return true");

    controller.searchField.setText("socks");
    assertFalse(controller.matchesSearchField(recipe), "Non-matching text should return false");
    }
  
    @Test
    void testLogOutButtonRedirectsToLogin(FxRobot robot) {
        robot.clickOn("#btnLogout");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Log out", stage.getTitle(),
                "Stage title should change to 'Log out' after clicking Log out button");
        
        assertNotNull(robot.lookup("#loginButton").query(),
                "Login.fxml should be loaded");

        assertEquals(null, SessionManager.getCurrentUser(), 
                "Session should be cleared on logout");
    }
  
    @ParameterizedTest 
    @EnumSource(ProjectStatus.class) 
    void testThatCreateRecipeCardSwitchStatementWorksCorrectly(ProjectStatus status, FxRobot robot) { 
        MyRecipesController myController = new MyRecipesController(recipeClient);
        Recipe testRecipe = new Recipe(
            "status_" + status.getLabel(),
            "userid",
            "some title",
            "some description", 
            SkillLevel.BEGINNER, 
            YarnType.WOOL, 
            NeedleSize.SIZE_2_0,
            status 
        );

        VBox card = myController.createRecipeCard(testRecipe);

        assertNotNull(card, "Card should not be null for project status: " + status.toString());
        
        HBox row = (HBox) card.getChildren().get(0);
        Label statusLabel = (Label) row.getChildren().get(2);
        assertEquals(status.getLabel(),
            statusLabel.getText(),
            "Card status should equal ProjectStatus status!"
        );
    }
}
