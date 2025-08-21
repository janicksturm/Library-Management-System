package de.th_mannheim.informatik.libraryManagement.management;


import de.th_mannheim.informatik.libraryManagement.domain.user.Admin;
import de.th_mannheim.informatik.libraryManagement.domain.user.AdultUser;
import de.th_mannheim.informatik.libraryManagement.domain.user.StudentUser;
import de.th_mannheim.informatik.libraryManagement.domain.user.User;

import java.util.HashMap;

/**
 * This class is responsible for managing users.
 */
public class UserManagement {
   public HashMap<String, User> users = new HashMap<>();

    /**
     * Constructor for UserManagement objects.
     */
    public UserManagement() {
        initializeAdmin();
    }

    /**
     * This method creates a new user.
     * @param type
     * @param name
     * @return newUser
    */
    public String createUser(String type, String name) {
        User newUser;

        switch (type.toLowerCase()) {
            case "student" -> newUser = new StudentUser(name);
            case "adult" -> newUser = new AdultUser(name);
            case "admin" -> newUser = new Admin(name);
            default -> throw new InvalidUserTypeException("Ungültiger Benutzertyp: " + type);
        
        }
        users.put(newUser.getId(), newUser);
        return newUser.getId();
    }

    /**
     * This method displays a user.
     * @param id
     */
    public void displayUser(String id) {
        try {
            if (users.containsKey(id)) {
                User user = users.get(id);
                user.displayUserType();
            }
        } catch (Exception e) {
            throw new UserNotFoundException("Benutzer mit ID: " + id + " existiert nicht.");
        }        
    }

    private void initializeAdmin() {
        Admin admin = new Admin("Admin User");
        users.put(admin.getId(), admin);
        System.out.println("Admin-Benutzer wurde hinzugefügt: " + admin.getId());
    }
}

/**
 * This class is responsible for exceptions when the user type is invalid.
 *
 * @return InvalidUserTypeException
 * @throws RuntimeException
 */
class InvalidUserTypeException extends RuntimeException {
    public InvalidUserTypeException(String message) {
        super(message);
    }
}
