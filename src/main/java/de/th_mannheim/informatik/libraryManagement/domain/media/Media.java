package de.th_mannheim.informatik.libraryManagement.domain.media;

/**
 * Abstract class for media objects.
 * Contains all basic information about a media object.
 */
public abstract class Media {
    protected int id;
    protected String title;
    protected String author;
    protected int releaseDate;
    protected boolean available = true;

    /**
     * Constructor for media objects. 
     * 
     * @param id The ID of the media.
     * @param title The title of the media.
     * @param author The author of the media.
     * @param year The release year of the media.
     * @param genre The genre of the media.
     */
    public Media(int id, String title, String author, int releaseDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    /**
     * Returns the availability of the media.
     * 
     * @return true if the media is available, false otherwise.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Returns the title of the media.
     * 
     * @return title of the media.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the media.
     * 
     * @return author of the media.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Return the release year of the media.
     * 
     * @return release year of the media.
     */
    public int getReleaseDate() {
        return releaseDate;
    }

    /**
     * Returns the ID of the media.
     * 
     * @return ID of the media.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the availability of the media.
     * 
     * @param available true if the media is available, false otherwise.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Returns all important details of the media.
     * 
     * @return details of the media.
     */
    public abstract String displayMediaDetails();
}
