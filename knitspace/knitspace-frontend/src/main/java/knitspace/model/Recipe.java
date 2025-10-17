package knitspace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import knitspace.enums.NeedleSize;
import knitspace.enums.ProjectStatus;
import knitspace.enums.SkillLevel;
import knitspace.enums.YarnType;

/**
 * Record for Recipe concept.
 * Primarily used for serialization and deserialization in Api Calls.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Recipe(
    @JsonProperty("recipeId") String recipeId,
    @JsonProperty("title") String title,
    @JsonProperty("description") String description,
    @JsonProperty("skillLevel") SkillLevel skillLevel,
    @JsonProperty("yarnType") YarnType yarnType,
    @JsonProperty("needleSize") NeedleSize needleSize,
    @JsonProperty("projectStatus") ProjectStatus projectStatus,
    @JsonProperty("userId") String userId) {
}
