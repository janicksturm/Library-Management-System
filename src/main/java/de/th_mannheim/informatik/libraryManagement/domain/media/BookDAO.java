package de.th_mannheim.informatik.libraryManagement.domain.media;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final Logger LOGGER = Logger.getLogger(BookDAO.class.getName());

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT isbn, title, author, year, available FROM BOOK";

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                long isbn = rs.getLong("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                boolean available = rs.getBoolean("available");

                Book book = new Book(isbn, title, author, year, available);
                books.add(book);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    public static boolean checkIfBookExists(long isbn) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("library_db");
            EntityManager em = emf.createEntityManager();
            Book existMedia = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            return existMedia != null;
        } catch (Exception e) {
            LOGGER.severe("Error checking if book exists: " + e.getMessage());
            return false;
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}