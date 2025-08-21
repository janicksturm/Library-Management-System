package de.th_mannheim.informatik.libraryManagement.domain.user;

/**
 * Admin class that extends User.
 * Admins are users that have the highest level of access to the system.
 */
public class Admin extends User {

    /**
     * Constructor for Admin objects.
     * 
     * @param name name of the admin.
     */
    public Admin(String name) {
        super(name);
        super.yearlyFee = 0;
    }

    /**
     * Returns all basic information about the admin.
     * 
     * @return basic information about the admin.
     */
    @Override
    public String displayUserType() {
        return "Admin: " + " " + name + " " + id;
    }

    /**
     * Processes a payment for a member.
     * 
     * @param memberID id of the member.
     * @param amount amount to be paid.
     * @return payment confirmation.
     */
    public String processPayment(String memberID, double amount) {
        return "Zahlung von " + amount + " € für Mitglied mit der ID " + memberID + " wurde verbucht.";
    }
}
