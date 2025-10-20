package knitspace.model;

import knitspace.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record for Recipe concept.
 * Primarily used to represent a Recipe when serializing/deserializing api calls.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Recipe(
    @JsonProperty("recipeId") String recipeId,
    @JsonProperty("userId") String userId,
    @JsonProperty("title") String title,
    @JsonProperty("description") String description,
    @JsonProperty("skillLevel") SkillLevel skillLevel,
    @JsonProperty("yarnType") YarnType yarnType,
    @JsonProperty("needleSize") NeedleSize needleSize,
    @JsonProperty("projectStatus") ProjectStatus projectStatus
) {
}
