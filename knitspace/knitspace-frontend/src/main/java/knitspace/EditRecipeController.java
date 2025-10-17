package knitspace;

/**
 * Controller for EditRecipe.fxml.
 */
public class EditRecipeController {
    String source;

    /**
     * Sets the source from which vindow the user navigated from.
     * 
     * @param source the source window identifier.
     */
    public void setSource(String source) {
        this.source = source;

        if ("ViewRecipe".equals(source)) {
            System.out.println("Navigated from ViewRecipe");
        } else if ("MyRecipes".equals(source)) {
            System.out.println("Navigated from MyRecipes");
        }
    }

    public String getSource() {
        return source;
    }
}
