package knitspace.client;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import knitspace.exception.ApiException;
import knitspace.model.Recipe;
import knitspace.model.User;

/**
 * Client for recipe-related API operations.
 * Handles READ operation of CRUD for recipes.
 */
public class RecipeClient extends ApiClient {
  private static final String RECIPES_ENDPOINT = "/api/recipes";

  /**
   * Fetches all recipes for a given user.
   *
   * @param user The user whose recipes to fetch.
   * @return List of recipes for the user.
   */
  public List<Recipe> getRecipesForUser(User user) throws ApiException {
    try {
      HttpRequest request = createRequestBuilder(
          RECIPES_ENDPOINT + "/user/" + user.userId().toString()).GET().build();

      System.out.println(
          "Sending request to " + RECIPES_ENDPOINT + "/user/" + user.userId().toString());
      HttpResponse<String> response = httpClient.send(request,
          HttpResponse.BodyHandlers.ofString());
      System.out.println("Received response with status code: " + response.statusCode());

      Recipe[] recipes = handleResponse(response, Recipe[].class);
      if (recipes == null) {
        return new ArrayList<>();
      }
      return new ArrayList<>(java.util.Arrays.asList(recipes));
    } catch (Exception e) {
      throw new ApiException("Failed to fetch recipes for user: " + e.getMessage(), e);
    }
  }

  /**
  * Deletes a recipe by its recipeId.
  *
  * @param recipeId UUID of the recipe to delete
  * @throws ApiException if deletion fails or backend returns non-204 status
  */
  public void deleteRecipe(UUID recipeId) throws ApiException {
    try {
      HttpRequest request = createRequestBuilder(
          RECIPES_ENDPOINT + "/" + recipeId.toString())
          .DELETE()
          .build();


      HttpResponse<String> response = httpClient.send(
          request, HttpResponse.BodyHandlers.ofString());

      int status = response.statusCode();

      switch (status) {
        case 204:
          return;
        
        case 404:
          throw new ApiException("Recipe not found (404)");

        case 400:
          throw new ApiException("Invalid recipe ID format (400)");

        default:
          throw new ApiException(
            "Unexpected error (status: " + status + ", body: " + response.body() + ")"
          );
      }

    } catch (IOException | InterruptedException e) {
      throw new ApiException("Error deleting recipe: " + e.getMessage(), e);
    }
  }

}
