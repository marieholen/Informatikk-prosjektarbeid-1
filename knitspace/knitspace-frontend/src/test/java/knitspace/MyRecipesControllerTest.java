package knitspace;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

@ExtendWith(MockitoExtension.class)
class MyRecipesControllerTest {

    @Mock
    private RecipeClient recipeClient;

    @BeforeEach
    void setup() {
        SessionManager.setCurrentUser(null);
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
}
