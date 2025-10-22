package knitspace.controller;

import knitspace.model.Recipe;
import knitspace.enums.*;
import knitspace.service.RecipeService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateRecipeOk() throws Exception {
        UUID recipeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Recipe updatedRecipe = new Recipe(
            userId,
            "Updated test scarf",
            "An updated scarf for testing",
            SkillLevel.EXPERT,
            YarnType.COTTON,
            NeedleSize.SIZE_8_5,
            ProjectStatus.FINISHED
        );

        when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(updatedRecipe);

        mockMvc.perform(put("/api/recipes/" + recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"" + userId + "\",\"title\":\"Updated test scarf\",\"description\":\"An updated scarf for testing\",\"skillLevel\":\"EXPERT\",\"yarnType\":\"COTTON\",\"needleSize\":\"SIZE_8_5\",\"projectStatus\":\"FINISHED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated test scarf"))
                .andExpect(jsonPath("$.description").value("An updated scarf for testing"))
                .andExpect(jsonPath("$.skillLevel").value("EXPERT"))
                .andExpect(jsonPath("$.yarnType").value("COTTON"))
                .andExpect(jsonPath("$.needleSize").value("SIZE_8_5"))
                .andExpect(jsonPath("$.projectStatus").value("FINISHED"));
    }

    @Test
    void testUpdateRecipeBadRequest() throws Exception {
    UUID recipeId = UUID.randomUUID();

    doThrow(new IllegalArgumentException("Invalid input"))
        .when(recipeService).saveRecipe(any());

    mockMvc.perform(put("/api/recipes/" + recipeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"userId\":\"invalid\",\"title\":\"\",\"description\":\"\"}"))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateRecipeThrowsException() throws Exception {
        UUID recipeId = UUID.randomUUID();

        doThrow(new RuntimeException("Unexpected error"))
            .when(recipeService).saveRecipe(any());

        mockMvc.perform(put("/api/recipes/" + recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"" + UUID.randomUUID() + "\",\"title\":\"Updated title\",\"description\":\"Updated description\",\"skillLevel\":\"EXPERT\",\"yarnType\":\"COTTON\",\"needleSize\":\"SIZE_8_5\",\"projectStatus\":\"FINISHED\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateRecipeInvalidRecipeId() throws Exception {
        mockMvc.perform(put("/api/recipes/invalid-uuid")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"" + UUID.randomUUID() + "\",\"title\":\"Updated title\",\"description\":\"Updated description\",\"skillLevel\":\"EXPERT\",\"yarnType\":\"COTTON\",\"needleSize\":\"SIZE_8_5\",\"projectStatus\":\"FINISHED\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateRecipeInternalServerError() throws Exception {
        UUID recipeId = UUID.randomUUID();

        doThrow(new RuntimeException("Unexpected server error"))
            .when(recipeService).saveRecipe(any());

        mockMvc.perform(put("/api/recipes/" + recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"" + UUID.randomUUID() + "\",\"title\":\"Updated title\",\"description\":\"Updated description\",\"skillLevel\":\"EXPERT\",\"yarnType\":\"COTTON\",\"needleSize\":\"SIZE_8_5\",\"projectStatus\":\"FINISHED\"}"))
                .andExpect(status().isBadRequest());
    }
}
