package knitspace;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import knitspace.client.RecipeClient;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.exception.ApiException;
import knitspace.meta.SessionManager;
import knitspace.model.Recipe;
import knitspace.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Controller for {@code MyRecipes.fxml}.
 * Handles user interaction on the "My recipes" page.
 */
public class MyRecipesController {
  private final RecipeClient recipeClient;

  @FXML private ScrollPane recipesScrollPane;
  @FXML private VBox recipesVBox;

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
    setupScrollPane();
    // Only display recipes if recipeClient is available (not null)
    if (recipeClient != null) {
      displayRecipes();
    }
  }

  /**
   * Configures the scroll pane so that the content fits both vertically and horizontally.
   */
  private void setupScrollPane() {
    recipesScrollPane.setFitToHeight(true);
    recipesScrollPane.setFitToWidth(true);
  }

  /**
   * Creates new "recipe cards" for all recipes and displays them on screen.
   *
   * @see createRecipeCard
   */
  void displayRecipes() {
    List<Recipe> recipes = loadRecipes();

    if (recipesVBox != null) {
      recipesVBox.getChildren().clear();

      for (Recipe recipe : recipes) {
        recipesVBox.getChildren().add(createRecipeCard(recipe));
      }
    }
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

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(root));
      stage.setTitle("Create New Recipe");
      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handles the "Logout" button click.
   */
  @FXML
  private void handleLogout() {
    throw new UnsupportedOperationException("Logout clicked (not implemented)");
  }

  /**
   * Loads all the recipes for the current user from by using an API call from
   * RecipeClient.
   *
   * @return List of recipes for the current user.
   */
  List<Recipe> loadRecipes() {
    User currentUser = SessionManager.getCurrentUser();

    // Return empty list if recipeClient is null (i. ex. during ttesting)
    if (recipeClient == null) {
      return new ArrayList<>();
    }

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

  /**
   * Creates a styled recipe card. Containing recipe details and a new button called "Details".
   * 
   * @param recipe the recipe to display.
   * @return a VBox representing the recipe card.
   */
  VBox createRecipeCard(Recipe recipe) {
    final double cardSpacing = 5.0;
    final double cardPadding = 10.0;

    VBox card = new VBox(cardSpacing);
    card.setPadding(new Insets(cardPadding));
    card.setStyle(
        "-fx-background-color: #ffffff;"
            + "-fx-border-color: lightgrey;"
            + " -fx-border-radius: 5;"
            + "-fx-background-radius: 5;"
            + " -fx-padding: 15;"
    );

    Label title = new Label(recipe.title());
    title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

    Label details = new Label(
        recipe.skillLevel().getDescription()
            + "  |  "
            + recipe.needleSize()
            + "  |  "
            + recipe.yarnType()
    );
    details.setStyle("-fx-text-fill: gray;");

    Label status = new Label(recipe.projectStatus().getLabel());
    status.setStyle(
        "-fx-text-fill: white;"
            + " -fx-padding: 3 8 3 8;"
            + " -fx-background-radius: 10;"
    );

    switch (recipe.projectStatus()) {
      case IN_PROGRESS -> status.setStyle(
          status.getStyle() + "-fx-background-color: rgba(230, 138, 77, 0.5);");
      case FINISHED -> status.setStyle(
          status.getStyle() + "-fx-background-color: rgba(163, 178, 124, 0.65);");
      case PLANNED -> status.setStyle(
          status.getStyle() + "-fx-background-color: rgba(138, 186, 211, 0.7);");
      case PAUSED -> status.setStyle(
          status.getStyle() + "-fx-background-color: rgba(162, 165, 167, 0.7);");
      case ABANDONED -> status.setStyle(
          status.getStyle() + "-fx-background-color: rgba(211, 138, 138, 0.7);");
      default -> {
        // Spotbugs require handling default case.
        // No styling if unknown status!
      }
    }

    HBox row = new HBox(10);
    row.setAlignment(Pos.CENTER_LEFT);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    row.getChildren().addAll(title, details, status, spacer);
    card.getChildren().add(row);

    return card;
  }
}
