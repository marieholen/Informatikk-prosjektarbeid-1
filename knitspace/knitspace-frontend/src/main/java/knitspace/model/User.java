package knitspace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record for User concept. Primarily used for serialization and deserialization in Api Calls.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record User(
    @JsonProperty("userId") String userId,
    @JsonProperty("username") String username,
    @JsonProperty("password") String password) {
}
