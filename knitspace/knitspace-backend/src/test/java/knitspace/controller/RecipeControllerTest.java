package knitspace.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.model.Recipe;
import knitspace.service.RecipeService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Unit tests for RecipeController
 */
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecipeService recipeService;

    @Test
    void shouldReturnRecipesWhenUserHasRecipes() throws Exception {
        UUID userId = UUID.randomUUID();
        Recipe recipe1 = new Recipe(
            userId,
            "Test scarf",
            "A really warm scarf only used for testing",
            SkillLevel.BEGINNER,
            YarnType.WOOL,
            NeedleSize.SIZE_2_0,
            ProjectStatus.PLANNED
        );

        Recipe recipe2 = new Recipe(
            userId,
            "Another test scarf",
            "A really cold scarf only used for testing",
            SkillLevel.EXPERT,
            YarnType.COTTON,
            NeedleSize.SIZE_8_5,
            ProjectStatus.ABANDONED
        );

        when(recipeService.getRecipesByUserId(userId))
                .thenReturn(Optional.of(List.of(recipe1, recipe2)));

        mockMvc.perform(get("/api/recipes/user/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test scarf"))
                .andExpect(jsonPath("$[1].title").value("Another test scarf"));
    }

    @Test
    void shouldReturnNoContentWhenUserExistsButHasNoRecipes() throws Exception {
        UUID userId = UUID.randomUUID();

        when(recipeService.getRecipesByUserId(userId))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/recipes/user/" + userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestWhenUserIdIsInvalid() throws Exception {
        mockMvc.perform(get("/api/recipes/user/not-valid-UUID")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateNewRecipe() throws Exception {
    UUID userId = UUID.randomUUID();
    Recipe newRecipe = new Recipe(
        userId,
        "New test scarf",
        "A new scarf for testing",
        SkillLevel.BEGINNER,
        YarnType.ACRYLIC,
        NeedleSize.SIZE_5_0,
        ProjectStatus.PLANNED
    );

    when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(newRecipe);

    mockMvc.perform(post("/api/recipes")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userId\":\"" + userId + "\",\"title\":\"New test scarf\",\"description\":\"A new scarf for testing\",\"skillLevel\":\"BEGINNER\",\"yarnType\":\"ACRYLIC\",\"needleSize\":\"SIZE_5_0\",\"projectStatus\":\"PLANNED\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("New test scarf"));
    }

    @Test
    void testCreateNewRecipeBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Invalid input"))
            .when(recipeService).saveRecipe(any());
        mockMvc.perform(post("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"invalid\",\"title\":\"\",\"description\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testCreateRecipeThrowsException() throws Exception {
        doThrow(new RuntimeException("Unexpected error"))
            .when(recipeService).saveRecipe(any());

        mockMvc.perform(post("/api/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"" + UUID.randomUUID() + "\",\"title\":\"Test\",\"description\":\"Test description\",\"skillLevel\":\"BEGINNER\",\"yarnType\":\"WOOL\",\"needleSize\":\"SIZE_5_0\",\"projectStatus\":\"PLANNED\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testUpdateRecipeBadRequest() throws Exception {
    UUID recipeId = UUID.randomUUID();

    doThrow(new IllegalArgumentException("Invalid input"))
        .when(recipeService).updateRecipe(any(UUID.class), any(Recipe.class));

    mockMvc.perform(put("/api/recipes/" + recipeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userId\":\"invalid\",\"title\":\"\",\"description\":\"\"}"))
        .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenRecipeDoesNotExist() throws Exception {
    UUID recipeId = UUID.randomUUID();

    when(recipeService.deleteRecipeById(recipeId)).thenReturn(false);

    mockMvc.perform(delete("/api/recipes/" + recipeId))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenRecipeIdIsInvalid() throws Exception {
    mockMvc.perform(delete("/api/recipes/not-a-valid-uuid"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateRecipe() throws Exception {
        UUID recipeId = UUID.randomUUID();
        
        when(recipeService.updateRecipeById(eq(recipeId), any(Recipe.class))).thenReturn(true);

        String json = "{\"recipeId\":\"" + recipeId + "\",\"title\":\"test\"}";

        mockMvc.perform(put("/api/recipes/" + recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentRecipe() throws Exception {
        UUID recipeId = UUID.randomUUID();
        Recipe updatedRecipe = new Recipe(
            recipeId,
            UUID.randomUUID(),
            "Updated Test Recipe",
            "Updated description",
            SkillLevel.INTERMEDIATE,
            YarnType.COTTON,
            NeedleSize.SIZE_4_0,
            ProjectStatus.IN_PROGRESS
        );

        when(recipeService.updateRecipeById(recipeId, updatedRecipe)).thenReturn(false);

        String recipeJson = """
            {
                "recipeId": "%s",
                "userId": "%s",
                "title": "Updated Test Recipe",
                "description": "Updated description",
                "skillLevel": "INTERMEDIATE",
                "yarnType": "COTTON",
                "needleSize": "SIZE_4_0",
                "projectStatus": "IN_PROGRESS"
            }
            """.formatted(updatedRecipe.getRecipeId(), updatedRecipe.getUserId());

        mockMvc.perform(put("/api/recipes/" + recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenUpdateRecipeIdIsInvalid() throws Exception {
        String recipeJson = """
            {
                "recipeId": "invalid-uuid",
                "userId": "also-invalid",
                "title": "Updated Test Recipe",
                "description": "Updated description",
                "skillLevel": "INTERMEDIATE",
                "yarnType": "COTTON",
                "needleSize": "SIZE_4_0",
                "projectStatus": "IN_PROGRESS"
            }
            """;

        mockMvc.perform(put("/api/recipes/not-a-valid-uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(recipeJson))
                .andExpect(status().isBadRequest());
    }
}
