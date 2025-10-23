package knitspace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.lang.reflect.Method;
import java.util.List;
import knitspace.client.RecipeClient;
import knitspace.meta.SessionManager;
import knitspace.model.Recipe;
import knitspace.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class MyRecipesControllerTest {
    @Mock
    private RecipeClient recipeClient;

    private Stage stage;

    @BeforeEach
    void setup() {
        SessionManager.setCurrentUser(null);
    }

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));
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

        assertEquals("Create New Recipe", stage.getTitle(),
                "Stage title should change to 'Create New Recipe' after clicking New Recipe button");

        assertNotNull(robot.lookup("#saveButton").query(),
                "EditRecipeView.fxml should be loaded and contain a Save button");
    }
}
