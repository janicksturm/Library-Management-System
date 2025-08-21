package de.th_mannheim.informatik.libraryManagement.domain.media;

/**
 * This class represents a BoardGame object.
 * It extends the Media class with all basic information about a board game.
 */
public class BoardGame extends Media implements Extendable {
    private static final int EXTENSION_PERIOD = 14; // 2 Weeks in days
    private static final int MAX_EXTENSION = 0; // Maximum number of extensions

    /**
     * Constructor for a board game with the specified properties.
     * 
     * @param id The ID of the board game.
     * @param title The title of the board game.
     * @param publisher The publisher of the board game.
     * @param releaseDate The release date of the board game. 
     */
    public BoardGame(int id, String title, String publisher, int releaseDate) {
        super(id, title, publisher, releaseDate);
    }

    /**
     * Returns the extension period of the board game.
     * 
     * @return extension period of the board game.
     */
    @Override
    public int getExtensionPeriod() {
        return EXTENSION_PERIOD;
    }

    /**
     * Returns the maximum number of renewals of the board game.
     * 
     * @return maximum number of renewals of the board game.
     */
    @Override
    public int getMaxRenewals() {
        return MAX_EXTENSION;
    }

    /**
     * Returns all details of the board game.
     * 
     * @return details of the board game.
     */
    @Override
    public String displayMediaDetails() {
        return ("Das Spiel: " + title + " ist aus dem Jahr " + releaseDate  + " und wurde von " + author + "veröffentlich." + ".\nVerfügbar: " + available);
    }
}
