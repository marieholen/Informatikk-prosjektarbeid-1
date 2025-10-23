package knitspace;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;

/**
 * Controller for responsible for handeling the edit recipe view.
 */
public class EditRecipeController {

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
  private void handleUploadPdf(ActionEvent event) {
    System.out.println("Upload PDF button clicked (not implemented)");
  }

  @FXML
  private void onSaveClicked(ActionEvent event) {
    System.out.println("Save button clicked (not implemented)");
  }

}
