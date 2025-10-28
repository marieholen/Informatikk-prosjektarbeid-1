package knitspace;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import knitspace.model.Recipe;

/**
 * Controller for Viewrecipe.fxml. Handles displaying a knitting recipe.
 */
public class ViewRecipeController {

  @FXML
  Button editButton;

  @FXML
  Button backButton;

  @FXML
  private Label titleLabel;

  @FXML
  private Label descriptionLabel;

  @FXML
  private Label skillLevelLabel;

  @FXML
  private Label yarnLabel;

  @FXML
  private Label needleLabel;

  @FXML
  private Label statusLabel;

  private Recipe selectedRecipe;

  /**
  * Initialize the controller and set up event handlers.
  */
  @FXML
  private void initialize() {
    editButton.setOnAction(e -> handleEditButtonClick());
  }

  /**
  * Set and load the recipe details into the view.
  *
  * @param recipe the Recipe object to load data from.
  */
  public void setAndLoadRecipe(Recipe recipe) {
    this.selectedRecipe = recipe;

    titleLabel.setText(recipe.title());
    descriptionLabel.setText(recipe.description());
    skillLevelLabel.setText(recipe.skillLevel().getDescription());
    yarnLabel.setText(recipe.yarnType().getLabel());
    needleLabel.setText(recipe.needleSize().getLabel());
    statusLabel.setText(recipe.projectStatus().getDescription());
  }

  /**
  * Handle the Edit button click event. Loads the EditRecipeView and passes
  * the selected recipe to it.
  */
  @FXML
  private void handleEditButtonClick() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass()
          .getResource("/knitspace/EditRecipeView.fxml"));
      Parent root = fxmlLoader.load();

      EditRecipeController editRecipeController = fxmlLoader.getController();
      editRecipeController.loadFromViewRecipe(selectedRecipe);

      Platform.runLater(() -> {
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.setScene(new Scene(root));
      });

      System.out.println("editButton clicked");

    } catch (IOException e) {
      showAlert("Error", "Error loading EditRecipeView: " + e.getMessage());
    }
  }

  /**
  * Show an error alert dialog.
  *
  * @param title the title of the alert
  * @param message the message to display
  */
  @FXML
  public void showAlert(String title, String message) {
    Platform.runLater(() -> {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(message);

      if (Platform.isFxApplicationThread()) {
        alert.show();
      } else {
        Platform.runLater(alert::show);
      }
    });
  }


  /**
  * Getters for testing purposes.
  */
  public Label getTitleLabel() {
    return titleLabel;
  }

  public Label getDescriptionLabel() {
    return descriptionLabel;
  }

  public Label getSkillLevelLabel() {
    return skillLevelLabel;
  }

  public Label getYarnLabel() {
    return yarnLabel;
  }

  public Label getNeedleLabel() {
    return needleLabel;
  }

  public Label getStatusLabel() {
    return statusLabel;
  }

  @FXML
  private void handleDeleteButton() {
    // TODO: implement later. Currently a stub for TestFX tests.
  }

  /**
   * Handles back button click event. Loads MyRecipes.
   */
  @FXML
  private void handleBackButton() throws IOException {
    Stage stage = (Stage) backButton.getScene().getWindow();
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/knitspace/MyRecipes.fxml"));
    Parent root = fxmlLoader.load();

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Your Recipes!");
  }
}