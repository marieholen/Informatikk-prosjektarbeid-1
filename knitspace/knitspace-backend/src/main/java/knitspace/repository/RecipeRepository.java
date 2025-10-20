package knitspace.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import knitspace.model.Recipe;
import org.springframework.stereotype.Repository;

/**
 * Jackson repository for Recipe entities.
 * Stores Recipe in /data/recipes.json using Jackson for serialization
 * Based on the persistency module for recipe persistence from release 2
 */
@Repository
public class RecipeRepository {
  private final ObjectMapper mapper = new ObjectMapper();
  private final File file = new File("data/recipes.json");

  /**
   * Finds all recipes.
   *
   * @return List of all recipes
   */
  public List<Recipe> findAll() throws IOException {
    return loadRecipes();
  }

  /**
   * Finds a recipe by its id.
   *
   * @param recipeId the id of the recipe
   * @return the recipe if found, otherwise null
   */
  public Recipe findById(UUID recipeId) throws IOException {
    return findAll().stream()
        .filter(r -> r.getRecipeId().equals(recipeId))
        .findFirst()
        .orElse(null);
  }

  /**
   * Finds all recipes that belong to a specific user.
   *
   * @param userId the user's id
   * @return list of recipes for the user
   */
  public List<Recipe> findByUserId(UUID userId) throws IOException {
    return findAll().stream()
        .filter(r -> r.getUserId() != null && r.getUserId().equals(userId))
        .collect(Collectors.toList());
  }

  /**
   * Checks if a recipe exists by title .
   *
   * @param title the recipe title
   * @return true if a recipe with the title exists
   */
  public boolean existsByTitle(String title) throws IOException {
    return findAll().stream()
        .anyMatch(r -> r.getTitle() != null && r.getTitle().equalsIgnoreCase(title));
  }

  /**
   * Saves a new recipe or updates an existing one based on recipeId.
   *
   * @param recipe the recipe to save
   * @return the saved recipe
   */
  public Recipe save(Recipe recipe) throws IOException {
    List<Recipe> recipes = loadRecipes();
    recipes.removeIf(existing -> existing.getRecipeId().equals(recipe.getRecipeId()));
    recipes.add(recipe);
    saveRecipes(recipes);
    return recipe;
  }

  /**
   * Deletes a recipe by its id.
   *
   * @param recipeId the id of the recipe to delete
   */
  public void deleteById(UUID recipeId) throws IOException {
    List<Recipe> recipes = loadRecipes();
    recipes.removeIf(r -> r.getRecipeId().equals(recipeId));
    saveRecipes(recipes);
  }

  /**
   * Reads recipes from JSON file.
   *
   * @return list of recipes from the file, or empty list if file doesn't exist or is empty
   */
  private List<Recipe> loadRecipes() throws IOException {
    if (file.exists()) {
      return new ArrayList<>(List.of(mapper.readValue(file, Recipe[].class)));
    }
    return new ArrayList<>();
  }

  /**
   * Saves recipes to JSON file.
   *
   * @param recipes list of recipes to write to file
   */
  public void saveRecipes(List<Recipe> recipes) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(file, recipes);
  }
}
