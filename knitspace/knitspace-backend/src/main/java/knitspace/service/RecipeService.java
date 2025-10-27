package knitspace.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import knitspace.model.Recipe;
import knitspace.model.User;
import knitspace.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Service layer for Recipe operations. Implements business logic for recipe management.
 */
@Service
@RequiredArgsConstructor
public class RecipeService {
  
  private final RecipeRepository recipeRepository;

  /**
   * Fetch all recipes for a specific user given their unique id.
   *
   * @param user user to search recipes for
   * @return Optional containing potential recipes.
   */
  public Optional<List<Recipe>> getRecipesByUserId(User user) {
    List<Recipe> recipes;
    try {
      recipes = recipeRepository.findByUserId(user.getUserId());
    } catch (Exception e) {
      return Optional.empty();
    }
    return recipes.isEmpty() ? Optional.empty() : Optional.of(recipes);
  }

  /**
   * Fetch all recipes for a specific user id.
   *
   * @param userId the user's id
   * @return Optional containing recipes if any were found.
   */
  public Optional<List<Recipe>> getRecipesByUserId(UUID userId) {
    List<Recipe> recipes;
    try {
      recipes = recipeRepository.findByUserId(userId);
    } catch (Exception e) {
      return Optional.empty();
    }
    return recipes.isEmpty() ? Optional.empty() : Optional.of(recipes);
  }

  /**
   * Deletes a recipe by its unique recipe id.
   *
   * @param recipeId the ID of the recipe to delete
   * @return true if the recipe was deleted, false if not found or error.
   */
  public boolean deleteRecipeById(UUID recipeId) {
    try {
      Recipe recipe = recipeRepository.findById(recipeId);
      if (recipe == null) {
        return false;
      }
      recipeRepository.deleteById(recipeId);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Updates an existing recipe by its unique recipe id.
   *
   * @param recipeId the ID of the recipe to update
   * @param updatedRecipe the recipe data to update with
   * @return true if the recipe was updated, false if not found or error
   */
  public boolean updateRecipeById(UUID recipeId, Recipe updatedRecipe) {
    try {
      Recipe existingRecipe = recipeRepository.findById(recipeId);
      if (existingRecipe == null) {
        return false;
      }

      if (existingRecipe.getRecipeId() != updatedRecipe.getRecipeId()) {
        return false;
      }
      
      recipeRepository.save(updatedRecipe);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }


}
