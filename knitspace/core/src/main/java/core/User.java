package core;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private final UUID userId;
    private String username;
    private String password;
    
    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    @JsonCreator
    public User(
        @JsonProperty("userId") UUID userId,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
    ) {
        this.userId = userId == null ? UUID.randomUUID() : userId;
        this.username = username;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
