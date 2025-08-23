import de.th_mannheim.informatik.libraryManagement.domain.data.AuthUserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Test
    public void testValidAuthentication() {
        String username = "testadmin";
        String password = "testpassword";
        String expectedRole = "ADMIN";
        String actualRole = AuthUserService.authenticate(username, password);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    public void testInvalidAuthentication() {
        String username = "testAdmin";
        String password = "wrongPassword";
        String actualRole = AuthUserService.authenticate(username, password);

        assertNull(actualRole);
    }

    @Test
    public void testEmptyCredentials() {
        String username = "";
        String password = "";
        String actualRole = AuthUserService.authenticate(username, password);

        assertNull(actualRole);
    }
}
