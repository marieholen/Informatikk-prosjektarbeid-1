package knitspace;

import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import knitspace.client.RecipeClient;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.exception.ApiException;
import knitspace.meta.SessionManager;
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

  @FXML 
  private ComboBox<String> needleSizeComboBox;

  @FXML 
  private ComboBox<String> statusComboBox;

  @FXML
  private Button saveButton;

  private Recipe currentRecipe;

  private final RecipeClient recipeClient;

  /**
   * Constructor for EditRecipeController.
   */
  public EditRecipeController() {
    this.recipeClient = new RecipeClient();
  }

  /**
   * Constructor for used in testing when mocking.
   *
   * @param recipeClient RecipeClient to use for API calls
   */
  public EditRecipeController(RecipeClient recipeClient) {
    this.recipeClient = recipeClient;
  }

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
    this.currentRecipe = null;

    titleField.clear();
    descriptionField.clear();
    difficultyComboBox.setValue(null);
    yarnTypeComboBox.setValue(null);
    needleSizeComboBox.setValue(null);
    statusComboBox.setValue(null);
    // storedPdfPath = null;
  }

  @FXML
  private void handleUploadPdf(ActionEvent event) {
    // TODO: implement later. Currently a stub for TestFX tests.
  }

  /**
   * Called when save button is clicked.
   * Validates user inputs, creates or updates recipe.
   *
   * @param event Triggered actionevent
   */
  @FXML
  void handleSaveButton(ActionEvent event) {
    try {
      if (!checkFormInputOk()) {
        String errorMessage = getValidationErrorMessage();
        showAlert("Validation Error", errorMessage);
        return;
      }

      String title = titleField.getText();
      String description = titleField.getText();
      SkillLevel skillLevel = getSelectedSkillLevel();
      YarnType yarnType = getSelectedYarnType();
      NeedleSize needleSize = getSelectedNeedleSize();
      ProjectStatus projectStatus = getSelectedProjectStatus();

      if (this.currentRecipe == null) {
        Recipe recipeToCreate = new Recipe(
            UUID.randomUUID().toString(),
            SessionManager.getCurrentUser().userId(),
            title,
            description,
            skillLevel,
            yarnType,
            needleSize,
            projectStatus
        );

        recipeClient.createRecipe(recipeToCreate);
        showAlert("Success", "Recipe created successfully!");

      } else {
        System.out.println(currentRecipe.recipeId());
        Recipe recipeToUpdate = new Recipe(
            currentRecipe.recipeId(),
            currentRecipe.userId(),
            title,
            description,
            skillLevel,
            yarnType,
            needleSize,
            projectStatus
        );

        recipeClient.updateRecipe(recipeToUpdate);
        showAlert("Success", "Recipe updated successfully!");
      }

    } catch (Exception e) {
      showAlert("Error", "Could not save recipe: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public Button getSaveButton() {
    return saveButton;
  }

  /**
   * Displays an alert dialog with the given title and message.
   *
   * @param title   The title of the alert.
   * @param message The message of the alert.
   */
  void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Validates the form fields.
   *
   * @return true if the validation passes, false otherwise
   */
  boolean checkFormInputOk() {
    return isValidTitle()
           && isValidDescription()
           && isDifficultySelected()
           && isYarnTypeSelected()
           && isNeedleSizeSelected()
           && isStatusSelected();
  }

  boolean isValidTitle() {
    return titleField.getText() != null && !titleField.getText().trim().isEmpty();
  }

  boolean isValidDescription() {
    return descriptionField.getText() != null && !descriptionField.getText().trim().isBlank();
  }
    
  boolean isDifficultySelected() {
    return difficultyComboBox.getValue() != null;
  }
    
  boolean isYarnTypeSelected() {
    return yarnTypeComboBox.getValue() != null;
  }
    
  boolean isNeedleSizeSelected() {
    return needleSizeComboBox.getValue() != null;
  }
    
  boolean isStatusSelected() {
    return statusComboBox.getValue() != null;
  }
  
  /**
   * Checks if input fields are valid, if not returns an error message.
   *
   * @return Validation error message, null if no validation error
   */
  String getValidationErrorMessage() {
    if (!isValidTitle()) {
      return "Title cannot be empty or null!";
    }
    if (!isValidDescription()) {
      return "Description cannot be empty or null!!";
    }
    if (!isDifficultySelected()) {
      return "Please select a difficulty level.";
    }
    if (!isYarnTypeSelected()) {
      return "Please select a yarn type.";
    }
    if (!isNeedleSizeSelected()) {
      return "Please select a needle size.";
    }
    if (!isStatusSelected()) {
      return "Please select a project status.";
    }
    return null;
  }


  /**
   * Returns the {@link Skillevel} currently selected in the combo box.
   *
   * @return the selected {@link Skillevel}, or {@code null} if none is selected.
   */
  private SkillLevel getSelectedSkillLevel() {
    String selected = difficultyComboBox.getValue();
    for (SkillLevel level : SkillLevel.values()) {
      if (level.getDescription().equals(selected)) {
        return level;
      }
    }
    return null;
  }

  /**
   * Returns the {@link YarnType} currently selected in the combo box.
   *
   * @return the selected {@link YarnType}, or {@code null} if none is selected.
   */
  private YarnType getSelectedYarnType() {
    String selected = yarnTypeComboBox.getValue();
    for (YarnType type : YarnType.values()) {
      if (type.getLabel().equals(selected)) {
        return type;
      }
    }
    return null;
  }

  /**
   * Returns the {@link NeedleSize} currently selected in the combo box.
   *
   * @return the selected {@link NeedleSize}, or {@code null} if none is selected.
   */
  private NeedleSize getSelectedNeedleSize() {
    String selected = needleSizeComboBox.getValue();
    for (NeedleSize size : NeedleSize.values()) {
      if (size.getLabel().equals(selected)) {
        return size;
      }
    }
    return null;
  }

  /**
   * Returns the {@link ProjectStatus} currently selected in the combo box.
   *
   * @return the selected {@link ProjectStatus}, or {@code null} if none is selected.
   */
  private ProjectStatus getSelectedProjectStatus() {
    String selected = statusComboBox.getValue();
    for (ProjectStatus status : ProjectStatus.values()) {
      if (status.getDescription().equals(selected)) {
        return status;
      }
    }
    return null;
  }
}
