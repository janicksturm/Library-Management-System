package de.th_mannheim.informatik.libraryManagement.domain.user;

/**
 * StudentUser class that extends User.
 * StudentUsers are users that are students.
 * 
 */
public class StudentUser extends User {

    /**
     * Constructor for StudentUser objects.
     * @param name name of the student.
     */
    public StudentUser(String name) {
        super(name);
        super.yearlyFee = yearlyFee / 2;
    }

    /**
     * Returns all basic information about the student.
     * @return user type.
     */
    @Override
    public String displayUserType() {
        return "Student: " + name + " " + id;
    }
}
