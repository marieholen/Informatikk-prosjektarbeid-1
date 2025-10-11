package knitspace_frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record User(
        @JsonProperty("userId") String userId,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password) {
}
