package knitspace.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import knitspace.model.Recipe;
import knitspace.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Recipe operations.
 * Provides endpoints for recipe management in the Knitspace application
 */
@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

  private final RecipeService recipeService;

  /**
   * Get all recipes given some user id.
   *
   * @param userId The user id
   * @return 200 OK with recipes, 204 No Content if none, 400 on bad id
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Recipe>> fetchUserRecipes(@PathVariable("userId") String userId) {
    try {
      UUID id = UUID.fromString(userId);
      Optional<List<Recipe>> userRecipes = recipeService.getRecipesByUserId(id);
      return userRecipes
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.noContent().build());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Create a new recipe.
   * 
   * @param recipe The recipe data
   * @return 200 OK with created recipe, 400 Bad Request on failure.
   */
  @PostMapping
  public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
    try {
      Recipe saved = recipeService.saveRecipe(recipe);
      return ResponseEntity.ok(saved);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Update an existing recipe.
   * 
   * @param resipeId The recipe id
   * @param recipe The updated recipe data
   * @return 200 OK with updated recipe, 400 Bad Request on failure.
   */
  @PutMapping("/{recipeId}")
  public ResponseEntity<Recipe> updateRecipe(@PathVariable String recipeId, @RequestBody Recipe recipe) {
    try {
      recipe.setRecipeId(UUID.fromString(recipeId));
      Recipe updated = recipeService.saveRecipe(recipe);
      return ResponseEntity.ok(updated);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
