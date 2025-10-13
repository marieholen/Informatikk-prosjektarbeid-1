package persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.User;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality for persisting and retreving user data from a JSON file. 
 */
public class UserPersistence {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File file = new File("../../data/users.json");

/**
 * Loads all users from the JSON file if it exists.
 *  If the file exists, the method reads its content and maps it to a list of {@link User} objects.
 * @return a list containing all User objects loaded from the file, or an empty list if the file does not exist. 
 * @throws Exception if an error occurs while reading og parsing the file
 */
    public static List<User> loadUsers() throws Exception {
        if (file.exists()) {
            return new ArrayList<>(List.of(mapper.readValue(file, User[].class)));
        }
        return new ArrayList<>();
    }
    /**
     * Saves a list of users to the JSON file. 
     * @param users the list of {@link User} objects to be saved to the file
     * @throws Exception if an error occurs while reading og parsing the file
     */
    public static void saveUsers(List<User> users) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
    }
    /**
     * Adds a new user to the existing list of users and saves the updated list to the JSON file. 
     * @param newUser the {@link User} object to be added to the list
     * @throws Exception if an error occurs while loading or saving user data
     */
    public static void addUser(User newUser) throws Exception {
        List<User> users = loadUsers();
        users.add(newUser);
        saveUsers(users);
    }
    
    /**
     * Deletes a user from the list based on the provided username.
     * @param username the username of the {@link User} to be removed 
     * @throws Exception if an error occurs while loading or saving user data 
     */
    public static void deleteUser(String username) throws Exception {
        List<User> users = loadUsers();
        users.removeIf(u -> u.getUsername().equals(username));
        saveUsers(users);
    }

    /**
     * Updates the information of an existing user based on the provided username.
     * @param username the username of the {@link User} to be updated
     * @param updatedUser the {@link User} object containing the updates information
     * @throws Exception if an error occurs while loading or saving user data
     */
    public static void updateUser(String username, User updatedUser) throws Exception {
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.set(i, updatedUser);
                break;
            }
        }
        saveUsers(users);
    }

    /**
     * retrives all users stored in the JSON file.
     * @return a list of all {@link User} objects loaded from the JSON file.
     * @throws Exception if an erroe occurs while reading the user data
     */
    public static List<User> getAllUsers() throws Exception {
        return loadUsers();
    }
}