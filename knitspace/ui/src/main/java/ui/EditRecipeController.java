package ui;

import core.Recipe;

public class EditRecipeController {
    private Recipe selectedRecipe; //this field will be used later

    public void setSelectedRecipe(Recipe recipe) {
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
    }

    public Recipe getSelectedRecipe() { //This can be removed when the rest of the controller is implemented
        return new Recipe(
            selectedRecipe.getRecipeId(),
            selectedRecipe.getUserId(),
            selectedRecipe.getTitle(),
            selectedRecipe.getDescription(),
            selectedRecipe.getSkillLevel(),
            selectedRecipe.getYarnType(),
            selectedRecipe.getNeedleSize(),
            selectedRecipe.getProjectStatus()
        );
    }
}
