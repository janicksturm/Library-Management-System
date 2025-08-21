package de.th_mannheim.informatik.libraryManagement.domain.media;

/**
 * This class represents a DvD object.
 * It extends the Media class with all basic information about a DvD.
 */
public class DvD extends Media implements Extendable {
    private static final int EXTENSION_PERIOD = 7; // Weeks in days
    private static final int MAX_EXTENSION = 0; // Maximum number of extensions

    /**
     * Constructor for a DvD with the specified properties.
     * 
     * @param id The ID of the DvD.
     * @param title The title of the DvD.
     * @param director The director of the DvD.
     * @param releaseDate The release date of the DvD. 
     */
    public DvD(int id, String title, String director, int releaseDate) {
        super(id, title, director, releaseDate);
    }

    /**
     * Returns the extension period of the DvD.
     * 
     * @return extension period of the DvD.
     */
    @Override
    public int getExtensionPeriod() {
        return EXTENSION_PERIOD;
    }

    /**
     * Returns the maximum number of renewals of the DvD.
     * 
     * @return maximum number of renewals of the DvD.
     */
    @Override
    public int getMaxRenewals() {
        return MAX_EXTENSION;
    }

    /**
     * Returns all details of the DvD.
     * 
     * @return details of the DvD.
     */
    @Override
    public String displayMediaDetails() {
        return ("Der Film: " + title + " ist aus dem Jahr " + releaseDate  + " und wurde von " + author + "veröffentlich." + ".\nVerfügbar: " + available);
    }
}
