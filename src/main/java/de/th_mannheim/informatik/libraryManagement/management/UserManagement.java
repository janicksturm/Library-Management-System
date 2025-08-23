package de.th_mannheim.informatik.libraryManagement.management;

import de.th_mannheim.informatik.libraryManagement.domain.data.AuthUserService;
import de.th_mannheim.informatik.libraryManagement.domain.data.CreateUserService;

import java.util.logging.Logger;

/**
 * This class is responsible for managing users.
 */
public class UserManagement {
    private static String role;
    private static String currentUser;
    private static final Logger LOGGER = Logger.getLogger(UserManagement.class.getName());
    /**
     * This method creates a new user.
    */
    public boolean createUser(String username, String password, String email) {
        return CreateUserService.createUser(username, password, email, "USER");
    }

    /**
     * This method authenticates a user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean authenticateUser(String username, String password) {
        String authenticatedRole = AuthUserService.authenticate(username, password);
        if (authenticatedRole != null) {
            currentUser = username;
            role = authenticatedRole;
            return true;
        }
        return false;
    }

    /**
     * This method returns the role of the authenticated user.
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * This method returns the username of the current user.
     * @return The username of the current user.
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * This method deletes a user.
     * @param username The username of the user to be deleted.
     * @return true if deletion is successful, false otherwise.
     */
    public boolean deleteUser(String username) {
        return CreateUserService.removeUser(username);
    }
}
