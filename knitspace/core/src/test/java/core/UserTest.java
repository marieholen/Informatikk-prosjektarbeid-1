package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("User Class Tests")
public class UserTest {

    private User user;
    private final String testUsername = "testuser";
    private final String testPassword = "testpass123";

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create user with username and password")
        void testBasicConstructor() {
            user = new User(testUsername, testPassword);

            assertNotNull(user.getUserId(), "User ID should not be null");
            assertEquals(testUsername, user.getUsername(), "Username should match");
            assertEquals(testPassword, user.getPassword(), "Password should match");
        }

        @Test
        @DisplayName("Should generate unique user IDs for different users")
        void testUniqueUserIds() {
            User user1 = new User("user1", "pass1");
            User user2 = new User("user2", "pass2");

            assertNotEquals(user1.getUserId(), user2.getUserId(),
                    "Different users should have different IDs");
        }

        @Test
        @DisplayName("Should create user with JSON constructor - all parameters provided")
        void testJsonConstructorWithAllParams() {
            UUID testId = UUID.randomUUID();
            user = new User(testId, testUsername, testPassword);

            assertEquals(testId, user.getUserId(), "User ID should match provided ID");
            assertEquals(testUsername, user.getUsername(), "Username should match");
            assertEquals(testPassword, user.getPassword(), "Password should match");
        }

        @Test
        @DisplayName("Should generate new UUID when null ID provided to JSON constructor")
        void testJsonConstructorWithNullId() {
            user = new User(null, testUsername, testPassword);

            assertNotNull(user.getUserId(), "User ID should not be null when null is provided");
            assertEquals(testUsername, user.getUsername(), "Username should match");
            assertEquals(testPassword, user.getPassword(), "Password should match");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "  ", "\t", "\n" })
        @DisplayName("Should handle null, empty, and whitespace usernames")
        void testConstructorWithVariousUsernames(String username) {
            user = new User(username, testPassword);

            assertEquals(username, user.getUsername(), "Username should be stored as provided");
            assertEquals(testPassword, user.getPassword(), "Password should match");
            assertNotNull(user.getUserId(), "User ID should still be generated");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "  ", "\t", "\n" })
        @DisplayName("Should handle null, empty, and whitespace passwords")
        void testConstructorWithVariousPasswords(String password) {
            user = new User(testUsername, password);

            assertEquals(testUsername, user.getUsername(), "Username should match");
            assertEquals(password, user.getPassword(), "Password should be stored as provided");
            assertNotNull(user.getUserId(), "User ID should still be generated");
        }
    }

    @Nested
    @DisplayName("Getter Method Tests")
    class GetterTests {

        @BeforeEach
        void setUp() {
            user = new User(testUsername, testPassword);
        }

        @Test
        @DisplayName("Should return correct user ID")
        void testGetUserId() {
            UUID userId = user.getUserId();

            assertNotNull(userId, "User ID should not be null");
            assertEquals(userId, user.getUserId(), "Should return same ID on multiple calls");
        }

        @Test
        @DisplayName("Should return correct username")
        void testGetUsername() {
            assertEquals(testUsername, user.getUsername(), "Should return correct username");
        }

        @Test
        @DisplayName("Should return correct password")
        void testGetPassword() {
            assertEquals(testPassword, user.getPassword(), "Should return correct password");
        }
    }

    @Nested
    @DisplayName("Setter Method Tests")
    class SetterTests {

        @BeforeEach
        void setUp() {
            user = new User(testUsername, testPassword);
        }

        @Test
        @DisplayName("Should update username correctly")
        void testSetUsername() {
            String newUsername = "newusername";
            user.setUsername(newUsername);

            assertEquals(newUsername, user.getUsername(), "Username should be updated");
        }

        @Test
        @DisplayName("Should update password correctly")
        void testSetPassword() {
            String newPassword = "newpassword456";
            user.setPassword(newPassword);

            assertEquals(newPassword, user.getPassword(), "Password should be updated");
        }
    }
}
