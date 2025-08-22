package de.th_mannheim.informatik.libraryManagement.domain.media;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.logging.Logger;

public class CreateMediaService {
    public static final Logger LOGGER = Logger.getLogger(CreateMediaService.class.getName());

    public boolean createMedia(long isbn, String title, String author, int year, boolean status) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("library_db");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            if (mediaExists(title)) {
                LOGGER.warning("Media already exists: " + title);
                return false;
            }
            Book book = new Book(isbn, title, author, year, status);

            em.persist(book);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.severe("Error creating media: " + e.getMessage());
            return false;
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    private boolean mediaExists(String bookTitle) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("library_db");
            EntityManager em = emf.createEntityManager();
            Book existMedia = em.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class)
                    .setParameter("title", bookTitle)
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

    public void removeMedia(long isbn) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("library_db");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Book book = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();

            if (book != null) {
                em.remove(book);
                LOGGER.info("Media removed: " + book.getTitle());
            } else {
                LOGGER.warning("Media not found for removal: ISBN " + isbn);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.severe("Error removing media: " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
