package ui;

import java.io.IOException;

import core.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button backButton;

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
        skillLevelLabel.setText(selectedRecipe.getSkillLevel().getDescription());
        yarnLabel.setText(selectedRecipe.getYarnType().getLabel());
        needleLabel.setText(selectedRecipe.getNeedleSize().getLabel());
        statusLabel.setText(selectedRecipe.getProjectStatus().getDescription());
    }

    public void handleBackButtonClick() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyRecipes.fxml"));

        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, "Error when loading back to My Recipes window!:(");
            alert.showAndWait();
        }

    }
}
