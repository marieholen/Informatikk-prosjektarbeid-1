package persistency;

import core.User;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserPersistenceTest {
    @BeforeEach
    void cleanUp() throws Exception {
        // Remove all users before each test
        List<User> users = UserPersistence.loadUsers();
        for (User u : users) {
            UserPersistence.deleteUser(u.getUsername());
        }
    }

    @Test
    void testAddAndLoadUser() throws Exception {
        User user = new User(UUID.randomUUID(), "testuser", "password123");
        UserPersistence.addUser(user);
        List<User> users = UserPersistence.loadUsers();
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User(UUID.randomUUID(), "testuser", "password123");
        UserPersistence.addUser(user);
        UserPersistence.deleteUser("testuser");
        List<User> users = UserPersistence.loadUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User(UUID.randomUUID(), "testuser", "password123");
        UserPersistence.addUser(user);
        User updated = new User(user.getUserId(), "testuser", "newpass");
        UserPersistence.updateUser("testuser", updated);
        List<User> users = UserPersistence.loadUsers();
        assertEquals(1, users.size());
        assertEquals("newpass", users.get(0).getPassword());
    }
}
