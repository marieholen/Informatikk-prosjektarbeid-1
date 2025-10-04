package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;

import core.enums.NeedleSize;
import core.enums.ProjectStatus;
import core.enums.SkillLevel;
import core.enums.YarnType;
import core.User;
import core.SessionManager;

import core.Recipe;
import persistency.RecipePersistence;

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
    private Button uploadPdfButton;
    @FXML
    private Button saveButton;

    private File attachedPdf;

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

    @FXML
    private void onUploadPdfClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        attachedPdf = fileChooser.showOpenDialog(uploadPdfButton.getScene().getWindow());
    }

    @FXML
    private void onSaveClicked() {

        Recipe selectedRecipe = getSelectedRecipe();

        String title = titleField.getText();

        if (title == null || title.isBlank()) {
            showError("Title is required.");
            return;
        }

        try {
            if (RecipePersistence.recipeExists(title)) {
                showError("A recipe with this title already exists.");
                return;
            }

        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
        }

        if (attachedPdf == null) {
            showError("Please upload a PDF before saving.");
            return;
        }

        User loggedInUser = SessionManager.getCurrentUser();

        if (loggedInUser == null) {
            showError("You are not logged in");
            return;
        }

        Recipe recipe = new Recipe(
                loggedInUser.getUserId(),
                title,
                descriptionField.getText(),
                getSelectedSkillLevel(),
                getSelectedYarnType(),
                getSelectedNeedleSize(),
                getSelectedProjectStatus());

        try {
            RecipePersistence.updateRecipe(selectedRecipe.getRecipeId().toString(), recipe);
            showInfo("Recipe successfully updated!");
        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }

    private SkillLevel getSelectedSkillLevel() {
        String selected = difficultyComboBox.getValue();
        for (SkillLevel level : SkillLevel.values()) {
            if (level.getDescription().equals(selected)) {
                return level;
            }
        }
        return null;
    }

    private ProjectStatus getSelectedProjectStatus() {
        String selected = statusComboBox.getValue();
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getDescription().equals(selected)) {
                return status;
            }
        }
        return null;
    }

    private Recipe selectedRecipe; // this field will be used later

    public void setSelectedRecipe(Recipe recipe) {
        this.selectedRecipe = new Recipe(
                recipe.getRecipeId(),
                recipe.getUserId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getSkillLevel(),
                recipe.getYarnType(),
                recipe.getNeedleSize(),
                recipe.getProjectStatus());
    }

    public Recipe getSelectedRecipe() { // This can be removed when the rest of the controller is implemented
        return new Recipe(
                selectedRecipe.getRecipeId(),
                selectedRecipe.getUserId(),
                selectedRecipe.getTitle(),
                selectedRecipe.getDescription(),
                selectedRecipe.getSkillLevel(),
                selectedRecipe.getYarnType(),
                selectedRecipe.getNeedleSize(),
                selectedRecipe.getProjectStatus());
    }

    private YarnType getSelectedYarnType() {
        String selected = yarnTypeComboBox.getValue();
        for (YarnType type : YarnType.values()) {
            if (type.getLabel().equals(selected)) {
                return type;
            }
        }
        return null;
    }

    private NeedleSize getSelectedNeedleSize() {
        String selected = needleSizeComboBox.getValue();
        for (NeedleSize size : NeedleSize.values()) {
            if (size.getLabel().equals(selected)) {
                return size;
            }
        }
        return null;
    }
}
