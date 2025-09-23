package core;

import core.enums.SkillLevel;
import core.enums.ProjectStatus;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {
    private final UUID recipeId;
    private UUID userId;
    private String title;
    private String description;
    private SkillLevel skillLevel;
    private String yarnType;
    private String needleSize;
    private ProjectStatus projectStatus;

    public Recipe(UUID userId, String title, String description, SkillLevel skillLevel, String yarnType,
            String needleSize, ProjectStatus projectStatus) {
        this.recipeId = UUID.randomUUID();
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.skillLevel = skillLevel;
        this.yarnType = yarnType;
        this.needleSize = needleSize;
        this.projectStatus = projectStatus;
    }

    @JsonCreator
    public Recipe(
        @JsonProperty("recipeId") UUID recipeId,
        @JsonProperty("userId") UUID userId,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("skillLevel") SkillLevel skillLevel,
        @JsonProperty("yarnType") String yarnType,
        @JsonProperty("needleSize") String needleSize,
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

    public UUID getRecipeId() {
        return recipeId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getYarnType() {
        return yarnType;
    }

    public void setYarnType(String yarnType) {
        this.yarnType = yarnType;
    }

    public String getNeedleSize() {
        return needleSize;
    }

    public void setNeedleSize(String needleSize) {
        this.needleSize = needleSize;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Override
    public String toString() {
        return "Recipe [recipeId=" + recipeId + ", userId=" + userId + ", title=" + title + ", description="
                + description + ", skillLevel=" + skillLevel.getDescription() + ", yarnType=" + yarnType
                + ", needleSize=" + needleSize
                + ", projectStatus=" + projectStatus.getDescription() + "]";
    }

}