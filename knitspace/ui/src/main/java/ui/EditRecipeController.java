package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;

import core.enums.ProjectStatus;
import core.enums.SkillLevel;
import core.User;
import core.SessionManager;

import core.Recipe;
import persistency.RecipePersistence;

public class EditRecipeController {

    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> difficultyComboBox;
    @FXML private ComboBox<String> yarnTypeComboBox;
    @FXML private ComboBox<String> needleSizeComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Button uploadPdfButton;
    @FXML private Button saveButton;

    private File attachedPdf;

    @FXML
    private void initialize() {
        for (SkillLevel level : SkillLevel.values()) {
            difficultyComboBox.getItems().add(level.getDescription());
        }
        yarnTypeComboBox.getItems().addAll("Wool", "Cotton", "Linen", "Cashmere", "Alpaca", "Merino");
        needleSizeComboBox.getItems().addAll("2","2,5","3","3,5","4","4,5","5","5,5","6","6,5","7","7,5","8","8,5","9");
        for (ProjectStatus status : ProjectStatus.values()) {
            statusComboBox.getItems().add(status.getDescription());
        }

    }

    @FXML
    private void onUploadPdfClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        attachedPdf = fileChooser.showOpenDialog(uploadPdfButton.getScene().getWindow());
    }

    @FXML
    private void onSaveClicked() {
        String title = titleField.getText();

        // 1. Check if title is empty
        if (title == null || title.isBlank()) {
            showError("Title is required.");
            return;
        }

        try {
            // 2. Check if title already exists
            if (RecipePersistence.recipeExists(title)) {
                showError("A recipe with this title already exists.");
                return;
            }
            
        } catch (Exception e) {
            showError("An error occurred: " + e.getMessage());
        }

        // 3. Check if PDF is uploaded
        if (attachedPdf == null) {
            showError("Please upload a PDF before saving.");
            return;
        }

        User loggedInUser = SessionManager.getCurrentUser();

        // 4. Create recipe object (other fields optional)
        Recipe recipe = new Recipe(
            loggedInUser.getUserId(),
            title, 
            descriptionField.getText(),
            getSelectedSkillLevel(),
            yarnTypeComboBox.getValue(),
            needleSizeComboBox.getValue(),
            getSelectedProjectStatus()
        );

        try {
            // 5. Save via persistency module
            RecipePersistence.addRecipe(recipe);
            showInfo("Recipe saved successfully!");
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
}
