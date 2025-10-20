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
 * Service layer for Recipe operations.
 * Implements business logic for recipe management.
 */
@Service
@RequiredArgsConstructor
public class RecipeService {
  
  private final RecipeRepository recipeRepository;

  /**
   * Fetch all recipes for a specific user given their unique id.
   *
   * @param user user to search recipes for
   * @return Optional containing potential recipes
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
   * @return Optional containing recipes if any were found
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
}
