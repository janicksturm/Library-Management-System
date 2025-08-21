package de.th_mannheim.informatik.libraryManagement.management;


import de.th_mannheim.informatik.libraryManagement.domain.media.*;
import de.th_mannheim.informatik.libraryManagement.domain.user.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for managing the library, including media borrowing, returning,
 * fee calculations, and media catalog searches.
 */
public class LibraryManagement {
    private final HashMap<String, Media> mediaCollection = new HashMap<>();
    private final UserManagement userManagement;
    private final HashMap<String, LocalDate> borrowedDates = new HashMap<>();
    private final HashMap<String, Double> userFees = new HashMap<>();
    private static final LocalDate BORROWED_DATE = LocalDate.of(2024, 11, 1);

    /**
     * Constructor for LibraryManagement.
     *
     * @param userManagement UserManagement instance for managing users.
     */
    public LibraryManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
        initializeMediaCollection();
    }

    /**
     * Adds a media item to the collection.
     *
     * @param mediaId The ID of the media.
     * @param media The media object to be added.
     */
    public void addMedia(String mediaId, Media media) {
        if (!mediaCollection.containsKey(mediaId)) {
            mediaCollection.put(mediaId, media);
            System.out.println("Medium mit der ID " + mediaId + " wurde hinzugefügt.");
        } else {
            System.out.println("Medium mit der ID " + mediaId + " existiert schon.");
        }
    }

    /**
     * Allows a user to borrow a media item.
     * 
     * @param userId The ID of the user borrowing the media.
     * @param mediaId The ID of the media item to be borrowed.
     * @return True if the media was successfully borrowed, false otherwise.
     * @throws MediaUnavailableException if the media is not available.
     */
    public boolean borrowMedia(String userId, String mediaId) {
        User user = userManagement.users.get(userId);
        Media media = mediaCollection.get(mediaId);
    
        checkForUserMedia(user, media);
        if (!media.isAvailable()) {
            throw new MediaUnavailableException("Medium mit der ID " + mediaId + " nicht verfügbar.");
        }
    
        if (user.borrowMedia(mediaId)) {
            media.setAvailable(false);
            borrowedDates.put(mediaId, LocalDate.now());
            return true;
        }
        return false;
    }
    
    /**
     * Returns a media item that was borrowed by a user.
     * 
     * @param userId The ID of the user returning the media.
     * @param mediaId The ID of the media item to be returned.
     * @throws MediaNotFoundException if the media is not found.
     */
    public double returnMedia(String userId, String mediaId) {
        User user = userManagement.users.get(userId);
        Media media = mediaCollection.get(mediaId);
    
        checkForUserMedia(user, media);
    
        if (!user.returnMedia(mediaId)) {
            throw new MediaNotFoundException("Medium mit der ID " + mediaId + " wurde nicht ausgeliehen.");
        }
        media.setAvailable(true);
        return calculateFee(user);
    }
    
    /**
     * Displays the fees and borrowed items for a user.
     * 
     * @param userId The ID of the user to display the information for.
     * @return A list containing the fee as a string and the borrowed items.
     * @throws UserNotFoundException if the user is not found.
     */
    public List<String> getUserFeesAndBorrowedItems(String userId) {
        User user = userManagement.users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("Benutzer mit ID " + userId + " existiert nicht.");
        }

        String borrowedItems = user.getBorrowedMedia().isEmpty() ? "Keine ausgeliehenen Gegenstände." : String.join(", ", user.getBorrowedMedia());

        return List.of("Schulden: " + calculateFee(user) + " €", "Ausgeliehene Gegenstände: " + borrowedItems);
    }

    private double calculateFee(User user) {
        double totalFee = 0.0;
        LocalDate currentDate = LocalDate.of(2024, 11, 19);
    
        for (String mediaId : user.getBorrowedMedia()) {
            Media media = mediaCollection.get(mediaId);
    
            if (media instanceof Extendable extendable) {
                LocalDate dueDate = BORROWED_DATE.plusDays(extendable.getExtensionPeriod());
                long overdueDays = currentDate.toEpochDay() - dueDate.toEpochDay();
    
                if (overdueDays > 0) {
                    double fee = (overdueDays <= 7) ? overdueDays * 1.0 : (7 * 1.0) + ((overdueDays - 7) * 2.0);
                    totalFee += fee;
                }
            }
        }
        userFees.put(user.getId(), userFees.getOrDefault(user.getId(), 0.0) + totalFee);
        
        return totalFee;
    }

    /**
     * Processes a payment from a user to pay off fees.
     * 
     * @param userId The ID of the user making the payment.
     * @param amount The amount of the payment.
     * @return A list containing the payment result and remaining fees, if applicable.
     * @throws UserNotFoundException if the user is not found.
     */
    public List<String> processPayment(String userId, double amount) {
        if (!userManagement.users.containsKey(userId)) {
            throw new UserNotFoundException("Benutzer mit ID " + userId + " existiert nicht.");
        }

        double currentFee = userFees.getOrDefault(userId, 0.0);
        if (amount >= currentFee) {
            userFees.put(userId, 0.0);
            return List.of("Bezahlung von " + amount + " € war erfolgreich.", "Alle Schulden beglichen.");
        } else {
            double remainingFee = currentFee - amount;
            userFees.put(userId, remainingFee);
            return List.of("Teilzahlung von " + amount + " € erhalten.", "Restliche Schulden: " + remainingFee + " €");
        }
    }

    /**
     * Extend the loan period for a media item borrowed by a user.
     * 
     * @param userId The ID of the user extending the loan.
     * @param mediaId The ID of the media item to extend the loan for.
     * @return True if the loan was successfully extended, false otherwise.
     * @throws UserNotFoundException if the user is not found.
     * @throws MediaNotFoundException if the media is not found.
     * @throws LoanExtensionException if the media cannot be extended.
     */
    public boolean extendLoan(String userId, String mediaId) {
        User user = userManagement.users.get(userId);
        Media media = mediaCollection.get(mediaId);

        checkForUserMedia(user, media);
        if (!(media instanceof Extendable)) {
            throw new LoanExtensionException("Medium mit ID " + mediaId + " kann nicht verlängert werden.");
        }

        Extendable extendableMedia = (Extendable) media;
        if (user.borrowedMedia.contains(mediaId)) {
            int maxRenewals = extendableMedia.getMaxRenewals();
            LocalDate borrowedDate = borrowedDates.get(mediaId);
            if (borrowedDate != null && maxRenewals > 0) {
                borrowedDates.put(mediaId, borrowedDate.plusDays(extendableMedia.getExtensionPeriod()));
                return true;
            } else {
                throw new LoanExtensionException("Maximale Anzahl an Verlängerungen erreicht oder ungültiges Ausleihdatum.");
            }
        } else {
            return false;
        }
    }

    /**
     * Searches the media catalog for media items matching the specified title part.
     * 
     * @param titlePart The part of the title to search for.
     * @return List of strings representing the media items matching the search criteria.
     */
    public List<String> searchMedia(String titlePart) {
        return mediaCollection.values().stream()
                .filter(media -> media.getTitle().toLowerCase().contains(titlePart.toLowerCase()))
                .map(media -> String.format("ID: %s, Name: %s, Autor: %s, Medium: %s, Verfügbarkeit: %s",
                        media.getId(),
                        media.getTitle(),
                        media.getAuthor() != null ? media.getAuthor() : "-",
                        media.getClass().getSimpleName(),
                        media.isAvailable() ? "Verfügbar" : "Nicht verfügbar"))
                .collect(Collectors.toList());
    }

    private void checkForUserMedia(User user, Media media) {
        if (user == null) {
            throw new UserNotFoundException("Benutzer mit der angegeben ID existiert nicht.");
        }
        if (media == null) {
            throw new MediaNotFoundException("Medium mit der angegebenen ID nicht vorhanden.");
        }
    }
    private void initializeMediaCollection() {

        addMedia("1", new Book(1, "Effective Java", "Joshua Bloch", 2018));
        addMedia("2", new Book(2, "Clean Code", "Robert C. Martin", 2008));
        addMedia("3", new Book(3, "Design Patterns", "Erich Gamma et al.", 1994));
        addMedia("4", new Book(4, "Refactoring", "Martin Fowler", 1999));
        addMedia("5", new Book(5, "Java Concurrency in Practice", "Brian Goetz", 2006));
        addMedia("6", new DvD(6, "Inception", "Christopher Nolan", 2010));
        addMedia("7", new DvD(7, "The Matrix", "The Wachowskis", 1999));
        addMedia("8", new DvD(8, "Interstellar", "Christopher Nolan", 2014));
        addMedia("9", new DvD(9, "The Godfather", "Francis Ford Coppola", 1972));
        addMedia("10", new DvD(10, "Pulp Fiction", "Quentin Tarantino", 1994));
        addMedia("11", new Cd(11, "Greatest Hits", "Queen", 1981));
        addMedia("12", new Cd(12, "Thriller", "Michael Jackson", 1982));
        addMedia("13", new Cd(13, "Back in Black", "AC/DC", 1980));
        addMedia("14", new Cd(14, "Abbey Road", "The Beatles", 1969));
        addMedia("15", new Cd(15, "The Wall", "Pink Floyd", 1979));
        addMedia("16", new VideoGame(16, "The Legend of Zelda: Breath of the Wild", "Nintendo", 2017));
        addMedia("17", new VideoGame(17, "The Witcher 3: Wild Hunt", "CD Projekt Red", 2015));
        addMedia("18", new VideoGame(18, "Red Dead Redemption 2", "Rockstar Games", 2018));
        addMedia("19", new VideoGame(19, "Super Mario Odyssey", "Nintendo", 2017));
        addMedia("20", new VideoGame(20, "God of War", "Santa Monica Studio", 2018));
        addMedia("21", new BoardGame(21, "Catan", "Klaus Teuber", 1995));
        addMedia("22", new BoardGame(22, "Monopoly", "Charles Darrow", 1935));
        addMedia("23", new BoardGame(23, "Carcassonne", "Klaus-Jürgen Wrede", 2000));
        addMedia("24", new BoardGame(24, "Pandemic", "Matt Leacock", 2008));
        addMedia("25", new BoardGame(25, "Ticket to Ride", "Alan R. Moon", 2004));
        addMedia("26", new DvD(26, "Fight Club", "David Fincher", 1999));
        addMedia("27", new DvD(27, "The Shawshank Redemption", "Frank Darabont", 1994));
        addMedia("28", new DvD(28, "The Dark Knight", "Christopher Nolan", 2008));
        addMedia("29", new DvD(29, "Schindler's List", "Steven Spielberg", 1993));
        addMedia("30", new DvD(30, "Forrest Gump", "Robert Zemeckis", 1994));
        addMedia("31", new DvD(31, "The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", 2001));
        addMedia("32", new DvD(32, "The Lord of the Rings: The Two Towers", "Peter Jackson", 2002));
        addMedia("33", new DvD(33, "The Lord of the Rings: The Return of the King", "Peter Jackson", 2003));
        addMedia("34", new DvD(34, "Gladiator", "Ridley Scott", 2000));
        addMedia("35", new DvD(35, "Braveheart", "Mel Gibson", 1995));
        addMedia("36", new Cd(36, "Dark Side of the Moon", "Pink Floyd", 1973));
        addMedia("37", new Cd(37, "Led Zeppelin IV", "Led Zeppelin", 1971));
        addMedia("38", new Cd(38, "Rumours", "Fleetwood Mac", 1977));
        addMedia("39", new Cd(39, "Hotel California", "Eagles", 1976));
        addMedia("40", new Cd(40, "Born in the U.S.A.", "Bruce Springsteen", 1984));
        addMedia("41", new Cd(41, "Sgt. Pepper's Lonely Hearts Club Band", "The Beatles", 1967));
        addMedia("42", new Cd(42, "21", "Adele", 2011));
        addMedia("43", new Cd(43, "The Eminem Show", "Eminem", 2002));
        addMedia("44", new Cd(44, "Purple Rain", "Prince", 1984));
        addMedia("45", new Cd(45, "Nevermind", "Nirvana", 1991));
        addMedia("46", new VideoGame(46, "Minecraft", "Mojang", 2011));
        addMedia("47", new VideoGame(47, "Grand Theft Auto V", "Rockstar North", 2013));
        addMedia("48", new VideoGame(48, "Dark Souls III", "FromSoftware", 2016));
        addMedia("49", new VideoGame(49, "Fortnite", "Epic Games", 2017));
        addMedia("50", new VideoGame(50, "Hollow Knight", "Team Cherry", 2017));
        addMedia("51", new VideoGame(51, "Cyberpunk 2077", "CD Projekt Red", 2020));
        addMedia("52", new VideoGame(52, "Elden Ring", "FromSoftware", 2022));
        addMedia("53", new VideoGame(53, "Sekiro: Shadows Die Twice", "FromSoftware", 2019));
        addMedia("54", new VideoGame(54, "Overwatch", "Blizzard Entertainment", 2016));
        addMedia("55", new VideoGame(55, "League of Legends", "Riot Games", 2009));
        addMedia("56", new BoardGame(56, "Azul", "Michael Kiesling", 2017));
        addMedia("57", new BoardGame(57, "Dixit", "Jean-Louis Roubira", 2008));
        addMedia("58", new BoardGame(58, "7 Wonders", "Antoine Bauza", 2010));
        addMedia("59", new BoardGame(59, "Terraforming Mars", "Jacob Fryxelius", 2016));
        addMedia("60", new BoardGame(60, "Dominion", "Donald X. Vaccarino", 2008));
        addMedia("61", new BoardGame(61, "Risk", "Albert Lamorisse", 1957));
        addMedia("62", new BoardGame(62, "Clue", "Anthony E. Pratt", 1949));
        addMedia("63", new BoardGame(63, "Scrabble", "Alfred Butts", 1938));
        addMedia("64", new BoardGame(64, "Chess", "Unknown", 1475));
        addMedia("65", new BoardGame(65, "Gloomhaven", "Isaac Childres", 2017));
        addMedia("66", new DvD(66, "Jaws", "Steven Spielberg", 1975));
        addMedia("67", new DvD(67, "Jurassic Park", "Steven Spielberg", 1993));
        addMedia("68", new DvD(68, "Saving Private Ryan", "Steven Spielberg", 1998));
        addMedia("69", new DvD(69, "Star Wars: A New Hope", "George Lucas", 1977));
        addMedia("70", new DvD(70, "Star Wars: The Empire Strikes Back", "Irvin Kershner", 1980));
        addMedia("71", new DvD(71, "Star Wars: Return of the Jedi", "Richard Marquand", 1983));
        addMedia("72", new DvD(72, "Titanic", "James Cameron", 1997));
        addMedia("73", new DvD(73, "Avatar", "James Cameron", 2009));
        addMedia("74", new DvD(74, "The Silence of the Lambs", "Jonathan Demme", 1991));
        addMedia("75", new DvD(75, "The Sixth Sense", "M. Night Shyamalan", 1999));
        addMedia("76", new Cd(76, "Hysteria", "Def Leppard", 1987));
        addMedia("77", new Cd(77, "Appetite for Destruction", "Guns N' Roses", 1987));
        addMedia("78", new Cd(78, "Songs in the Key of Life", "Stevie Wonder", 1976));
        addMedia("79", new Cd(79, "Who's Next", "The Who", 1971));
        addMedia("80", new Cd(80, "The Joshua Tree", "U2", 1987));
        addMedia("81", new Cd(81, "Automatic for the People", "R.E.M.", 1992));
        addMedia("82", new Cd(82, "Bat Out of Hell", "Meat Loaf", 1977));
        addMedia("83", new Cd(83, "Blue", "Joni Mitchell", 1971));
        addMedia("84", new Cd(84, "Lemonade", "Beyoncé", 2016));
        addMedia("85", new Cd(85, "Abbey Road", "The Beatles", 1969));
        addMedia("86", new BoardGame(86, "Scythe", "Jamey Stegmaier", 2016));
        addMedia("87", new BoardGame(87, "Twilight Struggle", "Ananda Gupta & Jason Matthews", 2005));
        addMedia("88", new BoardGame(88, "Puerto Rico", "Andreas Seyfarth", 2002));
        addMedia("89", new BoardGame(89, "Brass: Birmingham", "Martin Wallace", 2018));
        addMedia("90", new BoardGame(90, "Through the Ages", "Vlaada Chvátil", 2006));
        addMedia("91", new BoardGame(91, "Spirit Island", "R. Eric Reuss", 2017));
        addMedia("92", new BoardGame(92, "Mage Knight", "Vlaada Chvátil", 2011));
        addMedia("93", new BoardGame(93, "Wingspan", "Elizabeth Hargrave", 2019));
        addMedia("94", new BoardGame(94, "The Crew: The Quest for Planet Nine", "Thomas Sing", 2019));
        addMedia("95", new BoardGame(95, "Root", "Cole Wehrle", 2018));
        addMedia("96", new VideoGame(96, "Portal 2", "Valve", 2011));
        addMedia("97", new VideoGame(97, "Half-Life 2", "Valve", 2004));
        addMedia("98", new VideoGame(98, "Super Smash Bros. Ultimate", "Nintendo", 2018));
        addMedia("99", new VideoGame(99, "The Elder Scrolls V: Skyrim", "Bethesda", 2011));
        addMedia("100", new VideoGame(100, "Metal Gear Solid V: The Phantom Pain", "Kojima Productions", 2015));
    }
}

/**
 * Exception thrown when a user is not found in the system.
 */
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

/**
 * Exception thrown when a media item is not found in the collection.
 */
class MediaNotFoundException extends RuntimeException {
    public MediaNotFoundException(String message) {
        super(message);
    }
}

/**
 * Exception thrown when a media item is unavailable for borrowing.
 */
class MediaUnavailableException extends RuntimeException {
    public MediaUnavailableException(String message) {
        super(message);
    }
}

/**
 * Exception thrown when an error occurs during a loan extension.
 */
class LoanExtensionException extends RuntimeException {
    public LoanExtensionException(String message) {
        super(message);
    }
}