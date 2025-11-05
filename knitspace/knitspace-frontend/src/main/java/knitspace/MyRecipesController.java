package knitspace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import knitspace.client.RecipeClient;
import knitspace.enums.ProjectStatus;
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

  @FXML
  private ScrollPane recipesScrollPane;

  @FXML
  private VBox recipesVerticalBox;

  @FXML
  TextField searchField;

  @FXML
  private Label numRecipesLabel;

  @FXML
  private Label completionRatioLabel;

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

    List<Recipe> recipes = loadRecipes();

    displayRecipes(recipes);
    fillAggregateMetrics(recipes);
  }

  /**
   * Configures the scroll pane so that the content fits both vertically and
   * horizontally.
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
  void displayRecipes(List<Recipe> recipes) {
    recipesVerticalBox.getChildren().clear();

    for (Recipe recipe : recipes) {
      recipesVerticalBox.getChildren().add(createRecipeCard(recipe));
    }
  }

  void fillAggregateMetrics(List<Recipe> recipes) {
    fillTotalRecipesMetric(recipes);
    fillCompletionRatioMetric(recipes);
  }

  @FXML
  private void fillTotalRecipesMetric(List<Recipe> recipes) {
    int totalRecipes = recipes.size();
    numRecipesLabel.setText(String.valueOf(totalRecipes));
  }

  private void fillCompletionRatioMetric(List<Recipe> recipes) {
    double completionRatio = recipes.stream()
        .filter(r -> r.projectStatus() == ProjectStatus.FINISHED)
        .count() / (double) recipes.size() * 100;

    String ratioText = String.format("%.0f%%", completionRatio);

    if (ratioText.equals("NaN%")) {
      completionRatioLabel.setText("0%");
    } else {
      completionRatioLabel.setText(ratioText);
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
   * Opens the ViewRecipe view for the selected recipe. Used when the "Details"
   * button is pressed.
   *
   * @param event  the triggered action event.
   * @param recipe the selected recipe to display the details for.
   */
  private void openRecipeDetails(ActionEvent event, Recipe recipe) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewRecipe.fxml"));
    try {
      Parent root = fxmlLoader.load();

      ViewRecipeController viewRecipeController = fxmlLoader.getController();

      viewRecipeController.setAndLoadRecipe(recipe);

      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(new Scene(root));
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.ERROR, "Error when loading recipe detail window!:(");
      alert.showAndWait();
    }
  }

  /**
   * Handles the "Logout" button click.
   *
   * @param event {@link ActionEvent} triggered by the button press
   */
  @FXML
  private void handleLogout(ActionEvent event) {
    try {
      SessionManager.setCurrentUser(null);

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
  List<Recipe> loadRecipes() {
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

  /**
   * Creates a styled recipe card. Containing recipe details and a new button
   * called "Details".
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
            + " -fx-padding: 15;");

    Label title = new Label(recipe.title());
    title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

    Label details = new Label(
        recipe.skillLevel().getDescription()
            + "  |  "
            + recipe.needleSize()
            + "  |  "
            + recipe.yarnType());
    details.setStyle("-fx-text-fill: gray;");

    Label status = new Label(recipe.projectStatus().getLabel());
    status.setStyle(
        "-fx-text-fill: white;"
            + " -fx-padding: 3 8 3 8;"
            + " -fx-background-radius: 10;");

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
        // No styling if unknown status.
      }
    }

    final Button detailsButton = new Button("Details");
    detailsButton.setOnAction(e -> openRecipeDetails(e, recipe));

    HBox row = new HBox(10);
    row.setAlignment(Pos.CENTER_LEFT);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    row.getChildren().addAll(title, details, status, spacer, detailsButton);
    card.getChildren().add(row);

    return card;
  }

  /**
   * Checks if the given recipe matches the current search query typed into the
   * search field.
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
