package gr2529.knitspace_backend.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr2529.knitspace_backend.model.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Jackson repository for User entities
 * Stores users in /data/users.json using Jackson for serialization
 * 
 * Is based on the persistency module for user persistence adapted to repository pattern.
 */
@Repository
public class UserRepository {
    
    private final ObjectMapper mapper = new ObjectMapper();
    private final File file = new File("data/users.json");

    /**
     * Find all users
     * 
     * @return List of all users
     */
    public List<User> findAll() throws IOException {
        return loadUsers();
    }

    /**
     * Check if a user exists by username
     * 
     * @param username The username to check
     * @return true if a user with the username exists
     */
    public boolean existsByUsername(String username) throws IOException {
        return findAll().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    /**
     * Saves a new user or updates an existing user based on userId
     * 
     * @param user The user to save
     * @return The saved user
     */
    public User save(User user) throws IOException {
        List<User> users = loadUsers();
        
        users.removeIf(existingUser -> existingUser.getUserId().equals(user.getUserId()));
        users.add(user);
        saveUsers(users);
        
        return user;
    }

    /**
     * Reads users from JSON file
     * 
     * @return List of users from the file, or empty list if file doesn't exist or is empty
     */
    private List<User> loadUsers() throws IOException {
        if (file.exists()) {
            return new ArrayList<>(List.of(mapper.readValue(file, User[].class)));
        }
        return new ArrayList<>();
    }

    /**
     * Save users to JSON file
     * 
     * @param users List of users to write to file
     */
    public void saveUsers(List<User> users) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
    }
}