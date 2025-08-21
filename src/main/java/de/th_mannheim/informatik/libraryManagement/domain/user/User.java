package de.th_mannheim.informatik.libraryManagement.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Abstract class for User
 * Contains all basic information about a user.
 */
public abstract class User {
    protected String name;
    protected String id;
    protected int yearlyFee;
    public List<String> borrowedMedia;

    /**
     * Constructor for User objects.
     * 
     * @param name name of the user.
     */
    public User(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.yearlyFee = 100;
        borrowedMedia = new ArrayList<>();
    }

    /**
     * Returns the name of the user.
     * 
     * @return name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the id of the user.
     * 
     * @return id of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the fee of the user.
     * 
     * @return fee of the user.
     */
    public int getMemberShipFee() {
        return yearlyFee;
    }

    /**
     * Returns all basic information about the user.
     * 
     * @return user type.
     */
    public abstract String displayUserType();

    /**
     * Borrows a media.
     * 
     * @param mediaId id of the media.
     * @return borrowing confirmation.
     */
    public boolean borrowMedia(String mediaId) {
        if (borrowedMedia.contains(mediaId)) {
            return false;
        }
        borrowedMedia.add(mediaId);
        return true;
    }

    /**
     * Returns a media.
     * 
     * @param mediaId id of the media.
     * @return return confirmation.
     */
    public boolean returnMedia(String mediaId) {
        if (borrowedMedia.contains(mediaId)) {
            borrowedMedia.remove(mediaId);
            return true;
        }
        return false;
    }

    public List<String> getBorrowedMedia() {
        return borrowedMedia;
    }
}
