package persistency;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import core.Recipe;
import core.enums.NeedleSize;
import core.enums.ProjectStatus;
import core.enums.SkillLevel;
import core.enums.YarnType;

public class RecipePersistenceTest {
    @BeforeEach
    void cleanUp()throws Exception {
        List<Recipe> recipes = RecipePersistence.loadRecipes();
        for (Recipe recipe : recipes) {
            RecipePersistence.deleteRecipe(recipe.getRecipeId().toString());
        }
    }

    @Test
    void testAddAndLoadRecipe() throws Exception {
        Recipe recipe = new Recipe(
            UUID.randomUUID(),
            "Spacious Hat",
            "Bob Marley rastacap",
            SkillLevel.BEGINNER,
            YarnType.WOOL,
            NeedleSize.SIZE_4_0,
            ProjectStatus.PLANNED
        );

        RecipePersistence.addRecipe(recipe);

        List<Recipe> loaded = RecipePersistence.loadRecipes();
        assertEquals(1, loaded.size());
        Recipe first = loaded.get(0);
        assertEquals("Spacious Hat", first.getTitle());
        assertEquals("Bob Marley rastacap", first.getDescription());
        assertEquals(SkillLevel.BEGINNER, first.getSkillLevel());
        assertEquals(YarnType.WOOL, first.getYarnType());
        assertEquals(NeedleSize.SIZE_4_0, first.getNeedleSize());
        assertEquals(ProjectStatus.PLANNED, first.getProjectStatus());
    }

    @Test
    void testDeleteRecipe() throws Exception {
        Recipe recipe = new Recipe(
            UUID.randomUUID(),
            "Long Scarf",
            "Just a very long scarf",
            SkillLevel.EASY,
            YarnType.ACRYLIC,
            NeedleSize.SIZE_8_0,
            ProjectStatus.IN_PROGRESS
        );

        RecipePersistence.addRecipe(recipe);
        RecipePersistence.deleteRecipe(recipe.getRecipeId().toString());

        List<Recipe> loaded = RecipePersistence.loadRecipes();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void testUpdateRecipe() throws Exception{
        Recipe original = new Recipe(
            UUID.randomUUID(),
            "Small Blanket",
            "Small blanket with seed stitch",
            SkillLevel.INTERMEDIATE,
            YarnType.COTTON,
            NeedleSize.SIZE_5_0,
            ProjectStatus.IN_PROGRESS
        );

        RecipePersistence.addRecipe(original);

        // same recipeId but all enums are changed
        Recipe changed = new Recipe(
                original.getRecipeId(), 
                UUID.randomUUID(),  
                "Miniature Blanket",             
                "Even smaller blanket than small blanket",
                SkillLevel.EXPERT,
                YarnType.CASHMERE,
                NeedleSize.SIZE_6_0,
                ProjectStatus.FINISHED
        );

        RecipePersistence.updateRecipe(original.getRecipeId().toString(), changed);

        List<Recipe> loaded = RecipePersistence.loadRecipes();
        assertEquals(1, loaded.size());
        Recipe r = loaded.get(0);
        assertEquals(original.getRecipeId(), r.getRecipeId());
        assertEquals("Miniature Blanket", r.getTitle());
        assertEquals("Even smaller blanket than small blanket", r.getDescription());
        assertEquals(changed.getUserId(), r.getUserId());
        assertEquals(SkillLevel.EXPERT, r.getSkillLevel());
        assertEquals(YarnType.CASHMERE, r.getYarnType());
        assertEquals(NeedleSize.SIZE_6_0, r.getNeedleSize());
        assertEquals(ProjectStatus.FINISHED, r.getProjectStatus());
    }

    @Test
    void testFindById() throws Exception {
        Recipe recipe1 = new Recipe(
            UUID.randomUUID(),
            "Socks",
            "Toe-up socks",
            SkillLevel.ADVANCED,
            YarnType.MERINO,
            NeedleSize.SIZE_2_5,
            ProjectStatus.PAUSED
        );

        Recipe recipe2 = new Recipe(
            UUID.randomUUID(),
            "Mittens",
            "Classic mittens",
            SkillLevel.BEGINNER,
            YarnType.WOOL,
            NeedleSize.SIZE_3_5,
            ProjectStatus.PLANNED
        );

        RecipePersistence.addRecipe(recipe1);
        RecipePersistence.addRecipe(recipe2);

        assertNull(RecipePersistence.findById(UUID.randomUUID().toString()));
        assertEquals(recipe1.getRecipeId(), RecipePersistence.findById(recipe1.getRecipeId().toString()).getRecipeId());
        assertEquals(recipe2.getRecipeId(), RecipePersistence.findById(recipe2.getRecipeId().toString()).getRecipeId());

    }

    @Test
    void testGetAllRecipes() throws Exception {
        RecipePersistence.addRecipe(new Recipe(
            UUID.randomUUID(), "Beanie", "K2P2 rib", SkillLevel.EASY, YarnType.ACRYLIC, NeedleSize.SIZE_4_5, ProjectStatus.PLANNED
        ));
        RecipePersistence.addRecipe(new Recipe(
            UUID.randomUUID(), "Cardigan", "Top-down raglan", SkillLevel.INTERMEDIATE, YarnType.WOOL, NeedleSize.SIZE_5_5, ProjectStatus.IN_PROGRESS
        ));
        List<Recipe> all = RecipePersistence.getAllRecipes();
        assertEquals(2, all.size());
        
    }
}
