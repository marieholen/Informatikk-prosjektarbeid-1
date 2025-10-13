package persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import core.Recipe;

/**
 * Provides functionality for persisting and retrieving pecipe data from JSON file. 
 */
public class RecipePersistence {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File file = new File("../../data/recipes.json");

    /**
     *  Saves a list of recipes to the JSON file. 
     * 
     * @param recipes the list of {@link Recipe} objects to be saved to the file
     * @throws Exception if an error occurs while writing to the file
     */
    public static void saveRecipes(List<Recipe> recipes) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, recipes);
    }

    /**
     * Loads all recipes from the JSON file if it exists. 
     * 
     * @return a list containing all {@link Recipe} objects loaded from the file, or an ampty list if the file does not exist
     * @throws Exception if an error occurs while reading og parsing the file
     */
    public static List<Recipe> loadRecipes() throws Exception {
        if (file.exists()) {
            return new ArrayList<>(Arrays.asList(mapper.readValue(file, Recipe[].class)));
        }
        return new ArrayList<>(); 
    }

    /**
     * Adds a new recipe to the existing list of recipes and saves the updated list to the JSON file
     * 
     * @param newRecipe the {@link Recipe} object to be added to the list
     * @throws Exception if an error occurs while loading or saving recipe data
     */
    public static void addRecipe(Recipe newRecipe) throws Exception {
        List<Recipe> recipes = loadRecipes();
        boolean exists = recipes.stream()
                .anyMatch(r -> r.getRecipeId().equals(newRecipe.getRecipeId()));
        if (!exists) {
            recipes.add(newRecipe);
            saveRecipes(recipes);
        }
    }


    /**
     * Deletes a recipe from the list based on the provided recipe ID.
     * 
     * @param id the uniqe identifier of the {@link Recipe} to be deleted
     * @throws Exception if an error occurs while loading or saving recipe data
     */
    public static void deleteRecipe(String id) throws Exception {
        List<Recipe> recipes = loadRecipes();
        recipes.removeIf(r -> r.getRecipeId().toString().equals(id));
        saveRecipes(recipes);
    }

/**
 * Updates the information of an existing recipe based on the provided recipe ID.
 * 
 * @param id the uniqe identifier of the {@link Recipe} to be updated
 * @param updatedRecipe the {@link Recipe} object containing the updated information
 * @throws Exception if an error occurs while loading or saving recipe data
 */
    public static void updateRecipe(String id, Recipe updatedRecipe) throws Exception {
        List<Recipe> recipes = loadRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            Recipe recipeToCheck = recipes.get(i);
            if (recipeToCheck.getRecipeId().toString().equals(id)) {
                recipeToCheck.setUserId(updatedRecipe.getUserId());
                recipeToCheck.setTitle(updatedRecipe.getTitle());
                recipeToCheck.setDescription(updatedRecipe.getDescription());
                recipeToCheck.setSkillLevel(updatedRecipe.getSkillLevel());
                recipeToCheck.setYarnType(updatedRecipe.getYarnType());
                recipeToCheck.setNeedleSize(updatedRecipe.getNeedleSize());
                recipeToCheck.setProjectStatus(updatedRecipe.getProjectStatus());
                break;
            }
        }
        saveRecipes(recipes);
    }
/**
 * Finds and returns a recipe with the specified recipe ID.
 * 
 * @param id the unique identifier of the {@link Recipe} to find
 * @return the {@link Recipe} object matching the specified ID, or {@code null} if not found
 * @throws Exception if an error occurs while loading recipe data
 */
    public static Recipe findById(String id) throws Exception {
        return loadRecipes().stream()
                .filter(p -> p.getRecipeId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retrives all recipes stored in the JSON file. 
     * 
     * @return a list containing all {@link Recipe} objects loaded from the JSON file 
     * @throws Exception if an error occurs while reading the recipe data
     */
    public static List<Recipe> getAllRecipes() throws Exception {
        return loadRecipes();

    }

    /**
     * Checks whether a recipe with the specified title exist in the JSON file.
     * @param title the title of the {@link Recipe} to check for existence
     * @return {@code true} if a recipe with the specified title exists, {@code false} otherwise
     * @throws Exception if an error occurs while loading recipe data
     */
    public static boolean recipeExists(String title) throws Exception {
    return loadRecipes().stream()
        .anyMatch(r -> r.getTitle().equalsIgnoreCase(title));
}

}

