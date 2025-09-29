package persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import core.Recipe;

public class RecipePersistence {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File file = new File("data/recipes.json");

    public static void saveRecipes(List<Recipe> recipes) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, recipes);
    }

    public static List<Recipe> loadRecipes() throws Exception {
        if (file.exists()) {
            return List.of(mapper.readValue(file, Recipe[].class));
        }
        return List.of(); 
    }


    public static void addRecipe(Recipe newRecipe) throws Exception {
        List<Recipe> recipes = loadRecipes();
        boolean exists = recipes.stream()
                .anyMatch(r -> r.getRecipeId().equals(newRecipe.getRecipeId()));
        if (!exists) {
            recipes.add(newRecipe);
            saveRecipes(recipes);
        }
    }


    public static void deleteRecipe(String id) throws Exception {
        List<Recipe> recipes = loadRecipes();
        recipes.removeIf(r -> r.getRecipeId().toString().equals(id));
        saveRecipes(recipes);
    }


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

    public static Recipe findById(String id) throws Exception {
        return loadRecipes().stream()
                .filter(p -> p.getRecipeId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public static List<Recipe> getAllRecipes() throws Exception {
        return loadRecipes();

    }
}

