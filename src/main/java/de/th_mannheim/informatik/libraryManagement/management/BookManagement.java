package de.th_mannheim.informatik.libraryManagement.management;

import de.th_mannheim.informatik.libraryManagement.domain.media.Book;
import de.th_mannheim.informatik.libraryManagement.domain.media.BookDAO;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * BookManagement class for handling book-related operations in the Library Management System.
 * This class provides methods to display books in a table model.
 */
public class BookManagement {
    /**
     * Displays all books in the provided table model.
     *
     * @param model The DefaultTableModel to populate with book data.
     * @return true if books were displayed, false if no books were found.
     */
    public boolean displayBooks(DefaultTableModel model) {
        List<Book> books = BookDAO.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return false;
        }
        System.out.println("Books retrieved: " + books);
        for (Book book : books) {
            model.addRow(new Object[]{
                    book.getId(),
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getYear(),
                    book.isAvailable() ? "Available" : "Not Available"
            });
        }
        return true;
    }
}
