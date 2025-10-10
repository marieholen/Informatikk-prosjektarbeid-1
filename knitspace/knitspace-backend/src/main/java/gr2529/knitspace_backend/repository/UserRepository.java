package gr2529.knitspace_backend.repository;

import gr2529.knitspace_backend.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory repository for User entities
 */
@Repository
public class UserRepository {
    
    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();
    
    /**
     * Find all users
     * @return List of all users
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Check if a user exists by username
     * @param username The username to check
     * @return true if a user with the username exists
     */
    public boolean existsByUsername(String username) {
        return users.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    /**
     * Save a new user
     * @param user The user to save
     * @return The saved user
     */
    public User save(User user) {
        users.put(user.getUserId(), user);
        return user;
    }
}