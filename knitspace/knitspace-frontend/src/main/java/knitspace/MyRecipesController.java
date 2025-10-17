package knitspace;

import java.util.List;
import knitspace.client.RecipeClient;
import knitspace.meta.SessionManager;
import knitspace.model.Recipe;
import knitspace.model.User;

/**
 * Controller for MyRecipes.fxml.
 */
public class MyRecipesController {

  /**
   * Loads all the recipes for the current user from by using an API call from
   * RecipeClient.
   *
   * @return List of recipes for the current user.
   */
  private List<Recipe> loadRecipes() {
    User currentUser = SessionManager.getCurrentUser();
    List<Recipe> recipesForUser = RecipeClient.getRecipesForUser(currentUser);
    return applyFilters(recipesForUser);
  }

  /**
   * Applies filters to the list of recipes. **TODO: IMPLEMENT PROPERLY**
   *
   * @param recipes List of recipes to filter.
   * @return Filtered list of recipes.
   */
  private List<Recipe> applyFilters(List<Recipe> recipes) {
    return recipes;
  }
}
