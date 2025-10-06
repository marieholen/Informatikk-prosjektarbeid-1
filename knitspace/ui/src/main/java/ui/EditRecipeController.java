package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

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

    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> difficultyComboBox;
    @FXML private ComboBox<String> yarnTypeComboBox;
    @FXML private ComboBox<String> needleSizeComboBox;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private Button uploadPdfButton;
    @FXML private Button saveButton;
    @FXML private Button backButton;
    //Commented out because og spotbugs issue. Field will be used later
    // private File attachedPdf;

    private Recipe currentRecipe;
    private boolean isNewRecipe = false;

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

    public void NewRecipeMode() {
        isNewRecipe = true;
        currentRecipe = null;

        titleField.clear();
        descriptionField.clear();
        difficultyComboBox.setValue(null);
        yarnTypeComboBox.setValue(null);
        needleSizeComboBox.setValue(null);
        statusComboBox.setValue(null);
    }

    public void setSelectedRecipe(Recipe recipe) {
        isNewRecipe = false;
        this.currentRecipe = recipe;

        titleField.setText(recipe.getTitle());
        descriptionField.setText(recipe.getDescription());
        difficultyComboBox.setValue(recipe.getSkillLevel().getDescription());
        yarnTypeComboBox.setValue(recipe.getYarnType().getLabel());
        needleSizeComboBox.setValue(recipe.getNeedleSize().getLabel());
        statusComboBox.setValue(recipe.getProjectStatus().getDescription());
    }

    @FXML
    private void onUploadPdfClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        //Commented out because og spotbugs issue. Field will be used later
        // attachedPdf = fileChooser.showOpenDialog(uploadPdfButton.getScene().getWindow());
    }

    @FXML
    private void onSaveClicked() {
        String title = titleField.getText();
        if (title == null || title.isBlank()) {
            showError("Title is required.");
            return;
        }

        User user = SessionManager.getCurrentUser();
        if (user == null) {
            showError("You are not logged in.");
            return;
        }

        try {
            if (isNewRecipe) {
                if (RecipePersistence.recipeExists(title)) {
                    showError("A recipe with this title already exists.");
                    return;
                }

                Recipe newRecipe = new Recipe(
                    user.getUserId(),
                    title,
                    descriptionField.getText(),
                    getSelectedSkillLevel(),
                    getSelectedYarnType(),
                    getSelectedNeedleSize(),
                    getSelectedProjectStatus()
                );

                RecipePersistence.addRecipe(newRecipe);
                showInfo("Recipe successfully created!");
            } else {
                Recipe updated = new Recipe(
                    currentRecipe.getRecipeId(),
                    currentRecipe.getUserId(),
                    title,
                    descriptionField.getText(),
                    getSelectedSkillLevel(),
                    getSelectedYarnType(),
                    getSelectedNeedleSize(),
                    getSelectedProjectStatus()
                );

                RecipePersistence.updateRecipe(currentRecipe.getRecipeId().toString(), updated);
                showInfo("Recipe successfully updated!");
            }

            goToMyRecipesView();

        } catch (Exception e) {
            showError("Error saving recipe: " + e.getMessage());
        }
    }

    @FXML
    private void onBackClicked() {
        goToMyRecipesView();
    }

    private void goToMyRecipesView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MyRecipes.fxml"));
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to load recipe overview.");
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
            if (level.getDescription().equals(selected)) return level;
        }
        return null;
    }

    private YarnType getSelectedYarnType() {
        String selected = yarnTypeComboBox.getValue();
        for (YarnType type : YarnType.values()) {
            if (type.getLabel().equals(selected)) return type;
        }
        return null;
    }

    private NeedleSize getSelectedNeedleSize() {
        String selected = needleSizeComboBox.getValue();
        for (NeedleSize size : NeedleSize.values()) {
            if (size.getLabel().equals(selected)) return size;
        }
        return null;
    }

    private ProjectStatus getSelectedProjectStatus() {
        String selected = statusComboBox.getValue();
        for (ProjectStatus status : ProjectStatus.values()) {
            if (status.getDescription().equals(selected)) return status;
        }
        return null;
    }
}