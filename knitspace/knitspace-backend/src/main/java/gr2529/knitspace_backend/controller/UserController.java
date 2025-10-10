package gr2529.knitspace_backend.controller;

import gr2529.knitspace_backend.model.User;
import gr2529.knitspace_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST Controller for User operations.
 * Provides endpoints for user management in the Knitspace application
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    /**
     * Get user by username. Authenticates using username and password.
     * 
     * @param user The user data
     * @return The user if found, 403 if failed authentication
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        Optional<User> authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());
        return authenticatedUser.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    
    /**
     * Create a new user. If the username is unique, the user will be created successfully.
     * 
     * @param user The user data
     * @return The created user in response code 201 (CREATED) if the username is unique, 409 (CONFLICT) if username already exists
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user.getUsername(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}