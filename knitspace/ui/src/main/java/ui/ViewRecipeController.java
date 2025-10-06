package ui;

import java.io.IOException;
import java.util.Optional;
import core.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import persistency.RecipePersistence;
public class ViewRecipeController {
    private Recipe selectedRecipe;

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

    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    public void setAndLoadRecipe(Recipe recipe) {
        this.selectedRecipe = new Recipe(
            recipe.getRecipeId(),
            recipe.getUserId(),
            recipe.getTitle(),
            recipe.getDescription(),
            recipe.getSkillLevel(),
            recipe.getYarnType(),
            recipe.getNeedleSize(),
            recipe.getProjectStatus()
        );

        titleLabel.setText(recipe.getTitle());
        descriptionLabel.setText(recipe.getDescription());
        skillLevelLabel.setText(recipe.getSkillLevel().getDescription());
        yarnLabel.setText(recipe.getYarnType().getLabel());
        needleLabel.setText(recipe.getNeedleSize().getLabel());
        statusLabel.setText(recipe.getProjectStatus().getDescription());
    }


    @FXML
    public void handleDeleteButtonClick() throws Exception {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Recipe");
        confirmationAlert.setHeaderText("Are you sure you want to delete this recipe?");
        confirmationAlert.setContentText("This action cannot be undone!");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the recipe
            RecipePersistence.deleteRecipe(selectedRecipe.getRecipeId().toString());
            this.selectedRecipe = null;

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyRecipes.fxml"));
            try {
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.setScene(new Scene(root));

            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR, "Error when loading back to My Recipes window!:(");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleBackButtonClick() {
        navigateToMyRecipes();
    }

    @FXML
    private void handleEditButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditRecipeView.fxml"));
            Parent root = fxmlLoader.load();

            EditRecipeController editRecipeController = fxmlLoader.getController();
            editRecipeController.setSelectedRecipe(selectedRecipe);

            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Error loading Edit Recipe View: " + e.getMessage());
        }
    }

    private void navigateToMyRecipes() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MyRecipes.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Error loading My Recipes View: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR, msg);
        alert.showAndWait();
    }
}