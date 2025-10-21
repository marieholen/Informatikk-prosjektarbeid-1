package knitspace;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import java.nio.file.Path;
import knitspace.enums.*;
import knitspace.model.Recipe;

/**
 * Controller for EditRecipe.fxml.
 */
public class EditRecipeController {
    @FXML 
    private TextField titleField, descriptionField;
    @FXML 
    private ComboBox<String> difficultyComboBox, yarnTypeComboBox, needleSizeComboBox, statusComboBox;
    
    private Recipe currentRecipe;
    private Path storedPdfPath;
    
     /**
      * Initalize the controller.
      * Fills the combo boxes with values from {@link SkillLevel}, {@link YarnType}, {@link NeedleSize}, and {@link ProjectStatus} enums.
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
        storedPdfPath = null;
    }

    @FXML
    private void handleUploadPdf(ActionEvent event) {
        // TODO: implement later. Currently a stub for TestFX tests.
    }

    @FXML
    private void onSaveClicked(ActionEvent event) {
        // TODO: implement later. Currently a stub for TestFX tests.
    }

}
