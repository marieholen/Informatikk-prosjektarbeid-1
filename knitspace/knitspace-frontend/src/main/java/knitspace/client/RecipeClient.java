package knitspace.client;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
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
}
