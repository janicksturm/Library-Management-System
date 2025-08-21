package de.th_mannheim.informatik.libraryManagement.management;

import de.th_mannheim.informatik.libraryManagement.domain.data.AuthService;
import de.th_mannheim.informatik.libraryManagement.domain.data.CreateService;

/**
 * This class is responsible for managing users.
 */
public class UserManagement {
    private static String role;
    /**
     * This method creates a new user.
    */
    public boolean createUser(String username, String password, String email) {
        return CreateService.createUser(username, password, email, "USER");
    }

    /**
     * This method authenticates a user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean authenticateUser(String username, String password) {
        String authenticatedRole = AuthService.authenticate(username, password);
        if (authenticatedRole != null) {
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

    public boolean deleteUser(String username) {
        //TODO
    }
}
