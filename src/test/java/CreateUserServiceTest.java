
import de.th_mannheim.informatik.libraryManagement.domain.data.CreateUserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUserServiceTest {

    @Test
    public void testCreateUser() {
        boolean result = CreateUserService.createUser("john_doe", "password123", "john@example", "USER");
        assertTrue(result);
    }

    @Test
    public void testCreateUserWithExistingTitle() {
        boolean result = CreateUserService.createUser("user", "user123", "user@ss.com", "USER");
        assertFalse(result);
    }

    @AfterAll
    public static void removeTestData() {
        CreateUserService.removeUser("john_doe");
    }
}
