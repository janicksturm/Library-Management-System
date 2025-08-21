package de.th_mannheim.informatik.libraryManagement.domain.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Logger;

/**
 * CreateService class for handling user creation in the Library Management System.
 * This service interacts with the database to create new users.
 */
public class CreateService {
    private static final Logger LOGGER = Logger.getLogger(CreateService.class.getName());

    /**
     * This method creates a new user in the database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param email The email of the new user.
     * @param role The role of the new user.
     * @return true if the user was created successfully, false otherwise.
     */
    public static boolean createUser(String username, String password, String email, String role) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("library_db");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            if (userExists(username)) {
                LOGGER.warning("User already exists: " + username);
                return false;
            }
            User user = new User(username, password, email, role);

            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            LOGGER.severe("Error creating user: " + e.getMessage());
            return false;
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    private static boolean userExists(String username) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("library_db");
            EntityManager em = emf.createEntityManager();
            User user = em.find(User.class, username);
            return user != null;
        } catch (Exception e) {
            LOGGER.severe("Error checking if user exists: " + e.getMessage());
            return false;
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}