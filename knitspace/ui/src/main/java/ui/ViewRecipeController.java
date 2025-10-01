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
    private Label difficultyLabel;

    @FXML
    private Label yarnLabel;

    @FXML
    private Label needleLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Button deleteButton;

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

        titleLabel.setText(selectedRecipe.getTitle());
        descriptionLabel.setText(selectedRecipe.getDescription());
        difficultyLabel.setText(selectedRecipe.getSkillLevel().getDescription());
        yarnLabel.setText(selectedRecipe.getYarnType());
        needleLabel.setText(selectedRecipe.getNeedleSize());
        statusLabel.setText(selectedRecipe.getProjectStatus().getDescription());
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
}
