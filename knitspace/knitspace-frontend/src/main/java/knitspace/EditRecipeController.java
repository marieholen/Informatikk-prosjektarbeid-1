package knitspace;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.model.Recipe;

/**
 * Controller for responsible for handeling the edit recipe view.
 */
public class EditRecipeController {
  @FXML
  private TextField titleField;

  @FXML
  private TextField descriptionField;

  @FXML
  private ComboBox<String> difficultyComboBox;

  @FXML
  private ComboBox<String> yarnTypeComboBox;

  @FXML private ComboBox<String> difficultyComboBox;
  @FXML private ComboBox<String> yarnTypeComboBox;
  @FXML private ComboBox<String> needleSizeComboBox;
  @FXML private ComboBox<String> statusComboBox;

  /**
   * Initializes the edit recipe view.
   */
  @FXML
  private void initialize() {
    initializeDifficultyComboBox();
    initializeYarnTypeComboBox();
    initializeNeedleSizeComboBox();
    initializeStatusComboBox();
  }

  /**
   * Initializes the difficulty combo box with all the available skill levels.
   */
  private void initializeDifficultyComboBox() {
    if (difficultyComboBox != null) {
      for (SkillLevel level : SkillLevel.values()) {
        difficultyComboBox.getItems().add(level.getDescription());
      }
    }
  }

  /**
   * Initializes the yarn type combo box with all available yarn types.
   */
  private void initializeYarnTypeComboBox() {
    if (yarnTypeComboBox != null) {
      for (YarnType type : YarnType.values()) {
        yarnTypeComboBox.getItems().add(type.getLabel());
      }
    }
  }

  /**
   * Initializes the needle size combo box with all available needle sizes.
   */
  private void initializeNeedleSizeComboBox() {
    if (needleSizeComboBox != null) {
      for (NeedleSize size : NeedleSize.values()) {
        needleSizeComboBox.getItems().add(size.getLabel());
      }
    }
  }

  /**
   * Initializes the status combo box with all available project statuses.
   */
  private void initializeStatusComboBox() {
    if (statusComboBox != null) {
      for (ProjectStatus status : ProjectStatus.values()) {
        statusComboBox.getItems().add(status.getDescription());
      }
    }
  }

  @FXML
  private ComboBox<String> needleSizeComboBox;

  @FXML
  private ComboBox<String> statusComboBox;

  private Recipe currentRecipe;

  /**
  * Initalize the controller. Fills the combo boxes with values from
  * {@link SkillLevel}, {@link YarnType}, {@link NeedleSize}, and
  * {@link ProjectStatus} enums.
  */
  @FXML
  private void initialize() {
    for (SkillLevel level : SkillLevel.values()) {
      difficultyComboBox.getItems().add(level.getDescription());
    }
    for (YarnType type : YarnType.values()) {
      yarnTypeComboBox.getItems().add(type.getLabel());
    }
    for (NeedleSize size : NeedleSize.values()) {
      needleSizeComboBox.getItems().add(size.getLabel());
    }
    for (ProjectStatus status : ProjectStatus.values()) {
      statusComboBox.getItems().add(status.getDescription());
    }
  }

  /**
  * Load recipe data from ViewRecipeController.
  *
  * @param recipe the Recipe object to load data from.
  */
  public void loadFromViewRecipe(Recipe recipe) {
    this.currentRecipe = new Recipe(
            recipe.recipeId(),
            recipe.userId(),
            recipe.title(),
            recipe.description(),
            recipe.skillLevel(),
            recipe.yarnType(),
            recipe.needleSize(),
            recipe.projectStatus()
    );

    titleField.setText(recipe.title());
    descriptionField.setText(recipe.description());
    difficultyComboBox.setValue(recipe.skillLevel().getDescription());
    yarnTypeComboBox.setValue(recipe.yarnType().getLabel());
    needleSizeComboBox.setValue(recipe.needleSize().getLabel());
    statusComboBox.setValue(recipe.projectStatus().getDescription());
  }

  /**
  * Clear all input fields for creating a new recipe.
  */
  public void loadFromMyRecipes() {
    titleField.clear();
    descriptionField.clear();
    difficultyComboBox.setValue(null);
    yarnTypeComboBox.setValue(null);
    needleSizeComboBox.setValue(null);
    statusComboBox.setValue(null);
    // storedPdfPath = null;
  }

  /*     @FXML
  private void handleUploadPdf(ActionEvent event) {
    // TODO: implement later. Currently a stub for TestFX tests.
  }

  @FXML
  private void onSaveClicked(ActionEvent event) {
    // TODO: implement later. Currently a stub for TestFX tests.
  }

  @FXML
  private void handleUploadPdf(ActionEvent event) {
  System.out.println("Upload PDF button clicked (not implemented)");
  }

  @FXML
  private void onSaveClicked(ActionEvent event) {
  System.out.println("Save button clicked (not implemented)");
  } */
}
