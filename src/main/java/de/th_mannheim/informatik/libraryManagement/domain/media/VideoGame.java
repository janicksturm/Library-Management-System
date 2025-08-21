package de.th_mannheim.informatik.libraryManagement.domain.media;

/**
 * This class represents a VideoGame object.
 * It extends the Media class with all basic information about a video game.
 */
public class VideoGame extends Media implements Extendable {
    private static final int EXTENSION_PERIOD = 28; // Weeks in days
    private static final int MAX_EXTENSION = 1; // Maximum number of extensions

    /**
     * Constructor for a video game with the specified properties.
     * 
     * @param id The ID of the video game.
     * @param title The title of the video game.
     * @param developer The developer of the video game.
     * @param releaseDate The release date of the video game. 
     */
    public VideoGame(int id, String title, String developer, int releaseDate) {
        super(id, title, developer, releaseDate);
    }

    /**
     * Returns the extension period of the video game.
     * 
     * @return extension period of the video game.
     */
    @Override
    public int getExtensionPeriod() {
        return EXTENSION_PERIOD;
    }

    /**
     * Returns the maximum number of renewals of the video game.
     * 
     * @return maximum number of renewals of the video game.
     */
    @Override
    public int getMaxRenewals() {
        return MAX_EXTENSION;
    }

    /**
     * Returns all details of the video game.
     * 
     * @return details of the video game.
     */
    @Override
    public String displayMediaDetails() {
        return ("Das Spiel: " + title + " ist aus dem Jahr " + releaseDate + " und wurde von " + author + "veröffentlich." + ".\nVerfügbar: " + available);
    }
}
