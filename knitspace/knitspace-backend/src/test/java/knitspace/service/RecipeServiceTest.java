package knitspace.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.model.Recipe;
import knitspace.model.User;
import knitspace.repository.RecipeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;


/**
 * Unit tests for RecipeService.
 */
@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void getRecipesByUserIdWithUuidReturnsRecipesWhenPresent() throws Exception {
        UUID userId = UUID.randomUUID();
        Recipe recipe = new Recipe(
            userId,
            "Test title",
            "A test description",
            SkillLevel.BEGINNER,
            YarnType.WOOL,
            NeedleSize.SIZE_7_0,
            ProjectStatus.ABANDONED
        );
        when(recipeRepository.findByUserId(userId)).thenReturn(List.of(recipe));

        Optional<List<Recipe>> result = recipeService.getRecipesByUserId(userId);

        assertThat(result).isPresent();
        assertThat(result.get()).hasSize(1);
        assertThat(result.get().get(0).getUserId()).isEqualTo(userId);
    }

    @Test
    void getRecipesByUserIdWithUuidReturnsEmptyWhenNoRecipes() throws Exception {
        UUID userId = UUID.randomUUID();
        when(recipeRepository.findByUserId(userId)).thenReturn(List.of());

        Optional<List<Recipe>> result = recipeService.getRecipesByUserId(userId);

        assertThat(result).isEmpty();
    }

    @Test
    void getRecipesByUserIdWithUuidReturnsEmptyOnException() throws Exception {
        UUID userId = UUID.randomUUID();
        when(recipeRepository.findByUserId(userId)).thenThrow(new IOException("Exception!:-("));

        Optional<List<Recipe>> result = recipeService.getRecipesByUserId(userId);

        assertThat(result).isEmpty();
    }

    @Test
    void getRecipesByUserWithUserReturnsRecipesWhenPresent() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "myUser", "myPassword");
        Recipe recipe = new Recipe(
            userId,
            "My Title",
            "My Description",
            SkillLevel.BEGINNER,
            YarnType.WOOL,
            NeedleSize.SIZE_2_0,
            ProjectStatus.PLANNED
        );
        when(recipeRepository.findByUserId(userId)).thenReturn(List.of(recipe));

        Optional<List<Recipe>> result = recipeService.getRecipesByUserId(user);

        assertThat(result).isPresent();
        assertThat(result.get()).hasSize(1);
        assertThat(result.get().get(0).getUserId()).isEqualTo(userId);
    }

    @Test
    void getRecipesByUser_withUser_returnsEmptyWhenNoRecipes() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "testUser", "testPassword");
        when(recipeRepository.findByUserId(userId)).thenReturn(List.of());

        Optional<List<Recipe>> result = recipeService.getRecipesByUserId(user);

        assertThat(result).isEmpty();
    }

    @Test
    void getRecipesByUser_withUser_returnsEmptyOnException() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "myFriend", "theirPassword");
        when(recipeRepository.findByUserId(any(UUID.class))).thenThrow(new IOException("EXCEPTION!!"));

        Optional<List<Recipe>> result = recipeService.getRecipesByUserId(user);

        assertThat(result).isEmpty();
    }

    @Test
    void saveRecipe_savesAndReturnsRecipe() throws Exception {
        UUID userId = UUID.randomUUID();
        Recipe recipeToSave = new Recipe(
            userId,
            "Test Title",
            "Test Description",
            SkillLevel.INTERMEDIATE,
            YarnType.COTTON,
            NeedleSize.SIZE_5_0,
            ProjectStatus.IN_PROGRESS
        );
        Recipe savedRecipe = new Recipe(
            recipeToSave.getUserId(),
            recipeToSave.getTitle(),
            recipeToSave.getDescription(),
            recipeToSave.getSkillLevel(),
            recipeToSave.getYarnType(),
            recipeToSave.getNeedleSize(),
            recipeToSave.getProjectStatus()
        );
        savedRecipe.setRecipeId(UUID.randomUUID());

        when(recipeRepository.save(recipeToSave)).thenReturn(savedRecipe);

        Recipe result = recipeService.saveRecipe(recipeToSave);

        assertThat(result).isNotNull();
        assertThat(result.getRecipeId()).isEqualTo(savedRecipe.getRecipeId());
        assertThat(result.getTitle()).isEqualTo("Test Title");
    }
    void deleteRecipeByIdReturnsTrueWhenRecipeExists() throws Exception {
    UUID recipeId = UUID.randomUUID();
    Recipe recipe = new Recipe(recipeId, UUID.randomUUID(), "title", "desc",
            SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_5_0, ProjectStatus.PLANNED);

    when(recipeRepository.findById(recipeId)).thenReturn(recipe);

    boolean result = recipeService.deleteRecipeById(recipeId);

    assertThat(result).isTrue();
    verify(recipeRepository).deleteById(recipeId);
    }

    @Test
    void deleteRecipeByIdReturnsFalseWhenRecipeNotFound() throws Exception {
    UUID recipeId = UUID.randomUUID();

    when(recipeRepository.findById(recipeId)).thenReturn(null);

    boolean result = recipeService.deleteRecipeById(recipeId);

    assertThat(result).isFalse();
    verify(recipeRepository, never()).deleteById(any());
    }

    @Test
    void deleteRecipeByIdReturnsFalseWhenExceptionThrown() throws Exception {
    UUID recipeId = UUID.randomUUID();

    when(recipeRepository.findById(recipeId)).thenThrow(new IOException("disk error!"));

    boolean result = recipeService.deleteRecipeById(recipeId);

    assertThat(result).isFalse();
    verify(recipeRepository, never()).deleteById(any());
    }

    @Test
    void updateRecipeByIdReturnsTrueWhenRecipeExistsAndIdsMatch() throws Exception {
        UUID recipeId = UUID.randomUUID();
        Recipe existingRecipe = new Recipe(recipeId, UUID.randomUUID(), "old title", "old desc",
                SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_3_0, ProjectStatus.PLANNED);
        Recipe updatedRecipe = new Recipe(recipeId, UUID.randomUUID(), "new title", "new desc",
                SkillLevel.EXPERT, YarnType.COTTON, NeedleSize.SIZE_5_0, ProjectStatus.FINISHED);

        when(recipeRepository.findById(recipeId)).thenReturn(existingRecipe);

        boolean result = recipeService.updateRecipeById(recipeId, updatedRecipe);

        assertThat(result).isTrue();
        verify(recipeRepository).save(updatedRecipe);
    }

    @Test
    void updateRecipeByIdReturnsFalseWhenRecipeNotFound() throws Exception {
        UUID recipeId = UUID.randomUUID();
        Recipe updatedRecipe = new Recipe(recipeId, UUID.randomUUID(), "title", "desc",
                SkillLevel.INTERMEDIATE, YarnType.ACRYLIC, NeedleSize.SIZE_4_0, ProjectStatus.IN_PROGRESS);

        when(recipeRepository.findById(recipeId)).thenReturn(null);

        boolean result = recipeService.updateRecipeById(recipeId, updatedRecipe);

        assertThat(result).isFalse();
        verify(recipeRepository, never()).save(any());
    }

    @Test
    void updateRecipeByIdReturnsFalseWhenRecipeIdsMismatch() throws Exception {
        UUID recipeId = UUID.randomUUID();
        UUID differentId = UUID.randomUUID();
        Recipe existingRecipe = new Recipe(recipeId, UUID.randomUUID(), "title", "desc",
                SkillLevel.BEGINNER, YarnType.WOOL, NeedleSize.SIZE_2_0, ProjectStatus.PLANNED);
        Recipe updatedRecipe = new Recipe(differentId, UUID.randomUUID(), "new title", "new desc",
                SkillLevel.EXPERT, YarnType.COTTON, NeedleSize.SIZE_6_0, ProjectStatus.FINISHED);

        when(recipeRepository.findById(recipeId)).thenReturn(existingRecipe);

        boolean result = recipeService.updateRecipeById(recipeId, updatedRecipe);

        assertThat(result).isFalse();
        verify(recipeRepository, never()).save(any());
    }

}
