import de.th_mannheim.informatik.libraryManagement.domain.media.BookDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BookDAOTest {

    @Test
    public void testCheckIfBookExists() {
        long existingIsbn = 9783608939700L;
        assertTrue(BookDAO.checkIfBookExists(existingIsbn));

    }

    @Test
    public void testCheckIfBookExistsNonExistingIsbn() {
        long nonExistingIsbn = 9876543210987L;
        assertFalse(BookDAO.checkIfBookExists(nonExistingIsbn));
    }
}
