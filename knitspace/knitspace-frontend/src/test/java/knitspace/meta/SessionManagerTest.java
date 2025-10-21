package knitspace.meta;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import knitspace.model.User;

public class SessionManagerTest {

    @Test
    public void setAndGetCurrentUser_returnsCopyNotSameInstance() {
        User user = new User("id-1", "alice", "secret");
        SessionManager.setCurrentUser(user);

        User gotUser = SessionManager.getCurrentUser();
        assertNotNull(gotUser);
        assertEquals(user.userId(), gotUser.userId());
        assertEquals(user.username(), gotUser.username());
        assertEquals(user.password(), gotUser.password());

        assertNotSame(user, gotUser);
    }

    @Test
    public void setNull_clearsSession() {
        User user = new User("id-2", "bob", "pw");
        SessionManager.setCurrentUser(user);
        assertNotNull(SessionManager.getCurrentUser());

        SessionManager.setCurrentUser(null);
        assertNull(SessionManager.getCurrentUser());
    }
}
