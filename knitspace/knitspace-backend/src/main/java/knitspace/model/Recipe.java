package knitspace.model;

import java.util.UUID;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple Recipe entity for Knitspace backend.
 * based on core.Recipe from release 2.
 */
@Data
@NoArgsConstructor
public class Recipe {
    /**
     * The unique identifier of the Recipe
     */
    private UUID recipeId;

    /**
     * The unique identifier of the user that "owns" the recipe
     */
    private UUID userId;

    /**
     * The title of the recipe.
     */
    private String title;

    /**
     * An extended description of the recipe
     */
    private String description;

    /**
     * The recommended skill level for the recipe. 
     * @see knitspace.enums.SkillLevel.
     */
    private SkillLevel skillLevel;

    /**
     * The recommended yarn type for the recipe.
     * @see knitspace.enums.YarnType.
     */
    private YarnType yarnType;

    /**
     * The recommended needle size for the recipe@
     * @see knitspace.enums.NeedleSize.
     */
    private NeedleSize needleSize;

    /**
     * Current status for the project.
     * @see knitspace.enums.ProjectStatus.
     */
    private ProjectStatus projectStatus;

    /**
     * Constructor to create a new Recipe with a generated UUID.
     * 
     * @param userId the unique ID of the user who created this recipe
     * @param title the title of the recipe
     * @param description a description of the recipe
     * @param skillLevel the recommended skill level to complete the project
     * @param yarnType the recommended yarn type for this recipe
     * @param needleSizenthe recommended knitting needle size
     * @param projectStatus the current status of the project
     */
    public Recipe(UUID userId, String title, String description, SkillLevel skillLevel, YarnType yarnType,
            NeedleSize needleSize, ProjectStatus projectStatus) {
        this.recipeId = UUID.randomUUID();
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.skillLevel = skillLevel;
        this.yarnType = yarnType;
        this.needleSize = needleSize;
        this.projectStatus = projectStatus;
    }

    /**
     * Constructor to create a new Recipe with a generated UUID.
     * 
     * @param recipeId the unique identifier of the recipe.
     * @param userId the unique ID of the user who created this recipe
     * @param title the title of the recipe
     * @param description a description of the recipe
     * @param skillLevel the recommened skill level to complete the project
     * @param yarnType the recommended yarn type for this recipe
     * @param needleSizenthe recommended knitting needle size
     * @param projectStatus the current status of the project
     */
    public Recipe(UUID recipeId, UUID userId, String title, String description, SkillLevel skillLevel, YarnType yarnType,
            NeedleSize needleSize, ProjectStatus projectStatus) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.skillLevel = skillLevel;
        this.yarnType = yarnType;
        this.needleSize = needleSize;
        this.projectStatus = projectStatus;
    }
}
