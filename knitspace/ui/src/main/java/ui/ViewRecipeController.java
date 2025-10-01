package ui;

import core.Recipe;
import javafx.fxml.FXML;
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
}
