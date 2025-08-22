import de.th_mannheim.informatik.libraryManagement.domain.media.CreateMediaService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateMediaServiceTest {

    @Test
    public void testCreateMedia() {
        CreateMediaService service = new CreateMediaService();
        boolean result = service.createMedia(9780134685991L, "Effective Java", "Joshua Bloch", 2018, true);
        assertTrue(result);
    }

    @Test
    public void testCreateMediaWithExistingTitle() {
        CreateMediaService service = new CreateMediaService();
        service.createMedia(9780134685991L, "Effective Java", "Joshua Bloch", 2018, true);


        boolean result = service.createMedia(9780134685991L, "Effective Java", "Joshua Bloch", 2018, true);
        assertFalse(result);
    }
}
