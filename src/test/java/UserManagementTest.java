import de.th_mannheim.informatik.libraryManagement.management.UserManagement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserManagementTest {
    private static final UserManagement userManagement = new UserManagement();

    @Test
    public void testCreateUser() {
        assertTrue(userManagement.createUser("testuser", "password123", "test@gmail.com"));
    }

    @Test
    public void testAuthenticateUser() {
        userManagement.createUser("testuser2", "password12", "test2@gmail.com");
        assertTrue(userManagement.authenticateUser("testuser2", "password12"));
    }

    @AfterAll
    public static void deleteTestUser() {
        userManagement.deleteUser("testuser");
        userManagement.deleteUser("testuser2");
    }
}
