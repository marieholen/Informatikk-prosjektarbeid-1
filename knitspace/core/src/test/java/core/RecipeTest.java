package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.enums.NeedleSize;
import core.enums.ProjectStatus;
import core.enums.SkillLevel;
import core.enums.YarnType;

public class RecipeTest {

    private Recipe recipe;
    private final UUID testUserId = UUID.randomUUID();
    private final String testTitle = "Test Knitting Pattern";
    private final String testDescription = "A test description for knitting pattern";
    private final SkillLevel testSkillLevel = SkillLevel.INTERMEDIATE;
    private final YarnType testYarnType = YarnType.WOOL;
    private final NeedleSize testNeedleSize = NeedleSize.SIZE_5_0;
    private final ProjectStatus testProjectStatus = ProjectStatus.PLANNED;

    @BeforeEach
    void setUp() {
        recipe = new Recipe(testUserId, testTitle, testDescription,
                testSkillLevel, testYarnType, testNeedleSize, testProjectStatus);
    }

    @Test
    void testBasicConstructor() {
        assertNotNull(recipe.getRecipeId());
        assertEquals(testUserId, recipe.getUserId());
        assertEquals(testTitle, recipe.getTitle());
        assertEquals(testDescription, recipe.getDescription());
        assertEquals(testSkillLevel, recipe.getSkillLevel());
        assertEquals(testYarnType, recipe.getYarnType());
        assertEquals(testNeedleSize, recipe.getNeedleSize());
        assertEquals(testProjectStatus, recipe.getProjectStatus());
    }

    @Test
    void testJsonConstructor() {
        UUID testRecipeId = UUID.randomUUID();
        Recipe jsonRecipe = new Recipe(testRecipeId, testUserId, testTitle, testDescription,
                testSkillLevel, testYarnType, testNeedleSize, testProjectStatus);

        assertEquals(testRecipeId, jsonRecipe.getRecipeId());
        assertEquals(testUserId, jsonRecipe.getUserId());
    }

    @Test
    void testJsonConstructorWithNullId() {
        Recipe nullIdRecipe = new Recipe(null, testUserId, testTitle, testDescription,
                testSkillLevel, testYarnType, testNeedleSize, testProjectStatus);

        assertNotNull(nullIdRecipe.getRecipeId());
    }

    @Test
    void testUniqueRecipeIds() {
        Recipe recipe1 = new Recipe(testUserId, "Recipe 1", "Description 1",
                SkillLevel.BEGINNER, YarnType.COTTON, NeedleSize.SIZE_3_0, ProjectStatus.IN_PROGRESS);
        Recipe recipe2 = new Recipe(testUserId, "Recipe 2", "Description 2",
                SkillLevel.EXPERT, YarnType.ALPACA, NeedleSize.SIZE_8_0, ProjectStatus.FINISHED);

        assertNotEquals(recipe1.getRecipeId(), recipe2.getRecipeId());
    }

    @Test
    void testSetters() {
        UUID newUserId = UUID.randomUUID();
        String newTitle = "Updated Pattern";
        String newDescription = "Updated description";
        SkillLevel newSkillLevel = SkillLevel.EXPERT;
        YarnType newYarnType = YarnType.CASHMERE;
        NeedleSize newNeedleSize = NeedleSize.SIZE_8_5;
        ProjectStatus newProjectStatus = ProjectStatus.FINISHED;

        UUID originalRecipeId = recipe.getRecipeId();

        recipe.setUserId(newUserId);
        recipe.setTitle(newTitle);
        recipe.setDescription(newDescription);
        recipe.setSkillLevel(newSkillLevel);
        recipe.setYarnType(newYarnType);
        recipe.setNeedleSize(newNeedleSize);
        recipe.setProjectStatus(newProjectStatus);

        assertEquals(newUserId, recipe.getUserId());
        assertEquals(newTitle, recipe.getTitle());
        assertEquals(newDescription, recipe.getDescription());
        assertEquals(newSkillLevel, recipe.getSkillLevel());
        assertEquals(newYarnType, recipe.getYarnType());
        assertEquals(newNeedleSize, recipe.getNeedleSize());
        assertEquals(newProjectStatus, recipe.getProjectStatus());
        assertEquals(originalRecipeId, recipe.getRecipeId()); // ID should remain unchanged
    }

    @Test
    void testToString() {
        String result = recipe.toString();

        assertNotNull(result);
        assertTrue(result.contains("Recipe"));
        assertTrue(result.contains(testTitle));
        assertTrue(result.contains(testDescription));
    }
}