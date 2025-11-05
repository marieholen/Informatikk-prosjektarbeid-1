package knitspace.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import knitspace.client.RecipeClient;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.exception.ApiException;
import knitspace.model.Recipe;
import knitspace.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class RecipeClientTest {
  @Mock
  private HttpClient mockHttpClient;

  @Mock
  private HttpResponse<String> mockResponse;

  private RecipeClient recipeClient;

  @BeforeEach
  void setup() {
    this.recipeClient = new RecipeClient(mockHttpClient);
  }

  /**
   * Helper function to create test recipe.
   * 
   * @param testId userId of recipe
   * @return Recipe connected to user with id testId
   */
  private Recipe createTestRecipe(String testId) {
    return new Recipe(
        testId,
        UUID.randomUUID().toString(),
        "Test title",
        "Test description",
        SkillLevel.BEGINNER,
        YarnType.COTTON,
        NeedleSize.SIZE_2_0,
        ProjectStatus.PLANNED
    );
  }

  @Test
  void testGetRecipesForUserSuccess() throws Exception {
    User user = new User(UUID.randomUUID().toString(), "testUsername", "testPassword");

    String body = """
        [
          {
            "recipeId":"%s",
            "userId":"%s",
            "title":"Test title",
            "description":"Test description",
            "skillLevel":"BEGINNER",
            "yarnType":"COTTON",
            "needleSize":"SIZE_2_0",
            "projectStatus":"PLANNED"
          }
        ]
        """.formatted(UUID.randomUUID(), user.userId().toString());

    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(200);
    when(mockResponse.body()).thenReturn(body);

    List<Recipe> result = recipeClient.getRecipesForUser(user);

    assertEquals(1, result.size());
    assertEquals("Test title", result.get(0).title());
    assertEquals(SkillLevel.BEGINNER, result.get(0).skillLevel());
    assertEquals(ProjectStatus.PLANNED, result.get(0).projectStatus());
  }

  @Test
  void testGetRecipesForUserInternalErrorResponse() throws Exception {
    User user = new User(UUID.randomUUID().toString(), "testUsername", "testPassword");

    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(500);
    when(mockResponse.body()).thenReturn("Server error");

    assertThrows(ApiException.class, () -> recipeClient.getRecipesForUser(user));
  }

  @Test
  void testDeleteRecipeSuccess() throws Exception {
    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(204);

    assertDoesNotThrow(() -> recipeClient.deleteRecipe(UUID.randomUUID()));
  }

  @Test
  void testDeleteRecipeNotFound() throws Exception {
    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(404);

    ApiException ex = assertThrows(ApiException.class,
        () -> recipeClient.deleteRecipe(UUID.randomUUID()));

    assertTrue(ex.getMessage().contains("404"));
  }

  @Test
  void testUpdateRecipeBadRequest() throws Exception {
    Recipe recipe = createTestRecipe(UUID.randomUUID().toString());
    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(400);

    ApiException ex = assertThrows(ApiException.class,
        () -> recipeClient.updateRecipe(recipe));
    assertTrue(ex.getMessage().contains("400"));
  }

  @Test
  void testCreateRecipeSuccess() throws Exception {
    Recipe recipe = createTestRecipe(UUID.randomUUID().toString());
    String responseBody = """
        {
          "recipeId":"%s",
          "userId":"%s",
          "title":"Test title",
          "description":"Test description",
          "skillLevel":"BEGINNER",
          "yarnType":"COTTON",
          "needleSize":"SIZE_2_0",
          "projectStatus":"PLANNED"
        }
        """.formatted(recipe.recipeId(), recipe.userId());

    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(201);
    when(mockResponse.body()).thenReturn(responseBody);

    Recipe created = recipeClient.createRecipe(recipe);
    assertEquals("Test title", created.title());
    assertEquals("Test description", created.description());
    assertEquals(YarnType.COTTON, created.yarnType());
    assertEquals(SkillLevel.BEGINNER, created.skillLevel());
  }

  @Test
  void testCreateRecipeBadRequest() throws Exception {
    Recipe recipe = createTestRecipe(UUID.randomUUID().toString());

    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(400);

    assertThrows(ApiException.class, () -> recipeClient.createRecipe(recipe));
  }

  @Test
  void testCreateRecipeConflict() throws Exception {
    Recipe recipe = createTestRecipe(UUID.randomUUID().toString());

    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(409);

    assertThrows(ApiException.class, () -> recipeClient.createRecipe(recipe));
  }

  @Test
  void testCreateRecipeInternalServerError() throws Exception {
    Recipe recipe = createTestRecipe(UUID.randomUUID().toString());

    doReturn(mockResponse).when(mockHttpClient).send(any(), any());
    when(mockResponse.statusCode()).thenReturn(500);

    assertThrows(ApiException.class, () -> recipeClient.createRecipe(recipe));
  }
}
