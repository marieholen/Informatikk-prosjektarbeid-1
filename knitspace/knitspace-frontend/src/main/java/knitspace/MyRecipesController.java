package knitspace;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import knitspace.client.RecipeClient;
import knitspace.exception.ApiException;
import knitspace.meta.SessionManager;
import knitspace.model.Recipe;
import knitspace.model.User;


/**
 * Controller for {@code MyRecipes.fxml}.
 * Handles user interaction on the "My recipes" page.
 */
public class MyRecipesController {
  @FXML
  private Button btnNewrecipe;

  private final RecipeClient recipeClient;

  /**
   * Constructor for MyRecipesController.
   */
  public MyRecipesController() {
    this.recipeClient = new RecipeClient();
  }

  /**
   * Constructor used in testing when mocking.
   *
   * @param recipeClient The RecipeClient to use for API calls
   */
  public MyRecipesController(RecipeClient recipeClient) {
    this.recipeClient = recipeClient;
  }

  @FXML
  private void initialize() {
    List<Recipe> recipes = loadRecipes();
  }

  /**
   * Handles the "New Recipe" button click by redirecting to the
   * {@code EditRecipeView.fxml} page to create a new recipe.
   *
   * @param event {@link ActionEvent} triggered by the button press
   */
  @FXML
  private void handleNewRecipe(ActionEvent event) {

    System.out.println(getClass().getResource("/knitspace/EditRecipeView.fxml"));
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/EditRecipeView.fxml"));
      Parent root = loader.load();

      Platform.runLater(() -> {
        Stage stage = (Stage) btnNewrecipe.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create New Recipe");
      });

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles the "Logout" button click.
   *
   * @param event {@link ActionEvent} triggered by the button press
   */
  @FXML
  private void handleLogout(ActionEvent event) {
    System.out.println(getClass().getResource("/knitspace/Login.fxml"));
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/knitspace/Login.fxml"));
      Parent root = loader.load();

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.setTitle("Log out");
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads all the recipes for the current user from by using an API call from
   * RecipeClient.
   *
   * @return List of recipes for the current user.
   */
  private List<Recipe> loadRecipes() {
    User currentUser = SessionManager.getCurrentUser();

    try {
      List<Recipe> recipesForUser = recipeClient.getRecipesForUser(currentUser);
      return applyFilters(recipesForUser);
    } catch (ApiException e) {
      showAlert("Client Error", "Could not load recipes: " + e.getMessage());
      return new ArrayList<>();
    }
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

  /**
   * Shows an alert with the given title and message. Often used to show errors.
   *
   * @param title   The title of the alert.
   * @param message The message to display in the alert.
   */
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML TextField searchField;

  /**
   * Checks if the given recipe matches the current search query typed into the search field.
   *
   *
   * @param recipe the recipe to check
   * @return {@code true} if the recipe title contains the search text 
   *         (case-insensitive), {@code false} otherwise
  */
  boolean matchesSearchField(Recipe recipe) {
    String searchText = searchField.getText();

    if (searchText != null && !searchText.isBlank()) {
      return recipe.title().toLowerCase().contains(searchText.toLowerCase());
    }
    return true;
  }
}
