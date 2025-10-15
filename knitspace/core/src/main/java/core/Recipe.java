package core;

import core.enums.SkillLevel;
import core.enums.YarnType;
import core.enums.NeedleSize;
import core.enums.ProjectStatus;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a knitting recipe created by a user. 
 * <p>
 * A {@code Recipe} contains information about the recipe, including title, 
 * decription, skill level, yarn type, needle size and the current project status. 
 * Each recipe is uniquely identified by a {@link UUID recipeId} and linked to an user with userID. 
 * </p>
 */
public class Recipe {
    private final UUID recipeId;
    private UUID userId;
    private String title; 
    private String description;
    private SkillLevel skillLevel;
    private YarnType yarnType;
    private NeedleSize needleSize;
    private ProjectStatus projectStatus;

    /**
     * Constructs a new {@code Recipe} with the specified parameters. 
     * A random {@link UUID} is generated for the recipe. 
     * 
     * @param userId the unique ID of the user who created this recipe
     * @param title the title of the recipe
     * @param description an opptional description of the recipe
     * @param skillLevel the recommened skill level to complete the project
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
     * Constructs a {@code Recipe} object used for JSON deserialization. 
     * <p>
     * This constructor allows creating {@code Recipe} objekts from serialized JSON data
     * using Jackson annotations. If the provided {@code recipeID} is null, a new
     * random ID is generated. 
     * </p>
     * 
     * @param recipeId the unique identifier for the recipe
     * @param userId the ID of the user who owns this recipe
     * @param title the title of the recipe
     * @param description an opptional description of the recipe
     * @param skillLevel the recommened skill level to complete the project
     * @param yarnType the recommended yarn type for this recipe
     * @param needleSize recommended knitting needle size
     * @param projectStatus the current status of the project
     */

    @JsonCreator
    public Recipe(
        @JsonProperty("recipeId") UUID recipeId,
        @JsonProperty("userId") UUID userId,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("skillLevel") SkillLevel skillLevel,
        @JsonProperty("yarnType") YarnType yarnType,
        @JsonProperty("needleSize") NeedleSize needleSize,
        @JsonProperty("projectStatus") ProjectStatus projectStatus
    ) {
        this.recipeId = recipeId == null ? UUID.randomUUID() : recipeId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.skillLevel = skillLevel;
        this.yarnType = yarnType;
        this.needleSize = needleSize;
        this.projectStatus = projectStatus;
    }

    /**
     * Returns the unique ID of this recipe.
     * 
     * @return the recipe´s UUID
     */
    public UUID getRecipeId() {
        return recipeId;
    }

    /**
     * Returns the ID of the user who created this recipe.
     * 
     * @return the user´s UIID
     */
    public UUID getUserId() {
        return this.userId;
    }

    /**
     * Updates the user ID associated with this recipe. 
     * 
     * @param userId the new user ID
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * Returns the title of this recipe.
     * 
     * @return the recipe title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title of this recipe. 
     * 
     * @param title the new recipe title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of this recipe. 
     * 
     * @return the recipe description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of this recipe. 
     * 
     * @param description the new recipe description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the skill level recommended to complete this recipe. 
     * 
     * @return the skill level
     */
    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    /**
     * Updates the skill level required for this recipe. 
     * 
     * @param skillLevel the new skill level
     */
    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    /**
     * Returns the yarn type recommended for this recipe.
     * 
     * @return the yarn type
     */
    public YarnType getYarnType() {
        return yarnType;
    }

    /**
     * Updates the yarn type recommended for this recipe. 
     * 
     * @param yarnType the new yarn type
     */
    public void setYarnType(YarnType yarnType) {
        this.yarnType = yarnType;
    }

    /**
     * Returns the knitting needle size recommended for this recipe. 
     * 
     * @return the needle size
     */
    public NeedleSize getNeedleSize() {
        return needleSize;
    }

    /**
     * Updates the knitting needle size for this recipe. 
     * 
     * @param needleSize the new needle size
     */
    public void setNeedleSize(NeedleSize needleSize) {
        this.needleSize = needleSize;
    }

    /**
     * Returns the current project status of this recipe. 
     * 
     * @return the project status
     */
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    /**
     * Updates the project status of this recipe
     * 
     * @param projectStatus the new project status
     */
    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    /**
     * Returns a string representation of this recipe, including all relevant attributes. 
     * 
     * @return a readable string-representation of this recipe
     */
    @Override
    public String toString() {
        return "Recipe [recipeId=" + recipeId + ", userId=" + userId + ", title=" + title + ", description="
                + description + ", skillLevel=" + skillLevel.getDescription() + ", yarnType=" + yarnType
                + ", needleSize=" + needleSize
                + ", projectStatus=" + projectStatus.getDescription() + "]";
    }

}
