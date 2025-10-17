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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
