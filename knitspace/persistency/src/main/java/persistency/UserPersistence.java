package persistency;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File file = new File("users.json");

    public static List<User> loadUsers() throws Exception {
        if (file.exists()) {
            return new ArrayList<>(List.of(mapper.readValue(file, User[].class)));
        }
        return new ArrayList<>();
    }

    public static void saveUsers(List<User> users) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
    }

    public static void addUser(User newUser) throws Exception {
        List<User> users = loadUsers();
        users.add(newUser);
        saveUsers(users);
    }

    public static void deleteUser(String username) throws Exception {
        List<User> users = loadUsers();
        users.removeIf(u -> u.getUsername().equals(username));
        saveUsers(users);
    }

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

    public static List<User> getAllUsers() throws Exception {
        return loadUsers();
    }
}