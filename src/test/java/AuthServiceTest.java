import de.th_mannheim.informatik.libraryManagement.domain.data.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthServiceTest {

    @Test
    public void testValidAuthentication() {
        String username = "admin";
        String password = "admin123";
        String expectedRole = "ADMIN";

        String actualRole = AuthService.authenticate(username, password);

        assertTrue(actualRole.equals(expectedRole));
    }

    @Test
    public void testInvalidAuthentication() {
        String username = "admin";
        String password = "wrongpassword";

        String actualRole = AuthService.authenticate(username, password);

        assertTrue(actualRole == null);
    }
}
