package de.th_mannheim.informatik.libraryManagement.domain.media;

/**
 * This class represents a Cd object.
 * It extends the Media class with all basic information about a Cd.
 */
public class Cd extends Media implements Extendable {
    private static final int EXTENSION_PERIOD = 14; // Weeks in days
    private static final int MAX_EXTENSION = 0; // Maximum number of extensions

    /**
     * Constructor for a Cd with the specified properties.
     * 
     * @param id The ID of the Cd.
     * @param title The title of the Cd.
     * @param artist The artist of the Cd.
     * @param releaseDate The release date of the Cd. 
     */
    public Cd(int id, String title, String artist, int releaseDate) {
        super(id, title, artist, releaseDate);
    }

    /**
     * Returns the extension period of the Cd.
     * 
     * @return extension period of the Cd.
     */
    @Override
    public int getExtensionPeriod() {
        return EXTENSION_PERIOD;
    }

    /**
     * Returns the maximum number of renewals of the Cd.
     * 
     * @return maximum number of renewals of the Cd.
     */
    @Override
    public int getMaxRenewals() {
        return MAX_EXTENSION;
    }

    /**
     * Returns all details of the Cd.
     * 
     * @return details of the Cd.
     */
    @Override
    public String displayMediaDetails() {
        return ("Die CD: " + title + " ist aus dem Jahr " + releaseDate  + " und wurde von " + author + "veröffentlich." + ".\nVerfügbar: " + available);
    }
}
