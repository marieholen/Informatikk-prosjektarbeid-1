package core;

public class SessionManager {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        if (user == null) {
            currentUser = null;
        } else {
            currentUser = new User(user.getUserId(), user.getUsername(), user.getPassword());
        }
    }

    public static User getCurrentUser() {
        if (currentUser == null) {
            return null;
        }
        return new User(currentUser.getUserId(), currentUser.getUsername(), currentUser.getPassword());
    }
}
