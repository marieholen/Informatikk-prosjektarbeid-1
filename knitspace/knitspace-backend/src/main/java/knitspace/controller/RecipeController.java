package knitspace.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import knitspace.model.Recipe;
import knitspace.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   * @param recipe The recipe data.
   * @return 201 (CREATED) if the created recipe if successful,
   *         409 (CONFLICT) if recipe data is invalid,
   *         500 (INTERNAL SERVER ERROR) on server error
   */
  @PostMapping
  public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
    try {
      Recipe savedRecipe = recipeService.saveRecipe(recipe);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Deletes a recipe by its ID.
   *
   * @param recipeId the UUID of the recipe to delete, as a String
   * @return 204 No Content if deleted successfully,
   *         404 Not Found if the recipe does not exist,
   *         400 Bad Request if the provided ID is not a valid UUID
   */
  @DeleteMapping("/{recipeId}")
  public ResponseEntity<Void> deleteRecipe(@PathVariable("recipeId") String recipeId) {
    try {
      UUID id = UUID.fromString(recipeId);
      boolean deleted = recipeService.deleteRecipeById(id);
      if (deleted) {
        return ResponseEntity.noContent().build(); 
      } else {
        return ResponseEntity.notFound().build(); 
      }
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build(); 
    }
  }
  
}
