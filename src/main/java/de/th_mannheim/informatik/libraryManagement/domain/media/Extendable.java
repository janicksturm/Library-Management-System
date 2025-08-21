package de.th_mannheim.informatik.libraryManagement.domain.media;

/**
 * This interface represents an Extendable object.
 * It provides the extension period and the maximum number of renewals of the media.
 */
public interface Extendable{
    int getExtensionPeriod(); // Returns the extension period of the media
    int getMaxRenewals(); // Returns the maximum number of renewals of the media
}
