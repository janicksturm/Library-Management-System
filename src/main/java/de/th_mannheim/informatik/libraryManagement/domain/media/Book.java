package de.th_mannheim.informatik.libraryManagement.domain.media;

/**
 * This class represents a Book object.
 * It extends the Media class with all basic information about a book.
 */
public class Book extends Media implements Extendable {
    private static final int EXTENSION_PERIOD = 28; // 4 Weeks in days
    private static final int MAX_EXTENSION = 3; // Maximum number of extensions

    /**
     * Constructor for a book with the specified properties.
     * 
     * @param id The ID of the book.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param releaseDate The release date of the book. 
     */
    public Book(int id, String title, String author, int releaseDate) {
        super(id, title, author, releaseDate);
    }

    /**
     * Returns the extension period of the book.
     * 
     * @return extension period of the book.
     */
    @Override
    public int getExtensionPeriod() {
        return EXTENSION_PERIOD;
    }

    /**
     * Returns the maximum number of renewals of the book.
     * 
     * @return maximum number of renewals of the book.
     */
    @Override
    public int getMaxRenewals() {
        return MAX_EXTENSION;
    }

    /**
     * Returns all details of the book.
     * 
     * @return details of the book.
     */
    @Override
    public String displayMediaDetails() {
        return ("Das Buch: " + title + " ist aus dem Jahr " + releaseDate  + " und wurde von " + author + "veröffentlich." + ".\nVerfügbar: " + available);
    }
}
