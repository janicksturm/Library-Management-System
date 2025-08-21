package de.th_mannheim.informatik.libraryManagement.domain.user;

/**
 * AdultUser class that extends User.
 * AdultUsers are users that are adults.
 */
public class AdultUser extends User {

    /**
     * Constructor for AdultUser objects.
     * 
     * @param name name of the adult.
     */
    public AdultUser(String name) {
        super(name);
    }

    /**
     * Returns all basic information about the adult.
     * 
     * @return all basic information about the adult.
     */
    @Override
    public String displayUserType() {
        return "Adult: " + name + " " + id;
    }
}
