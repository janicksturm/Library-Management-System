package de.th_mannheim.informatik.libraryManagement.ui;


import de.th_mannheim.informatik.libraryManagement.domain.user.Admin;
import de.th_mannheim.informatik.libraryManagement.domain.user.User;
import de.th_mannheim.informatik.libraryManagement.management.LibraryManagement;
import de.th_mannheim.informatik.libraryManagement.management.UserManagement;

import java.util.List;
import java.util.Scanner;

/**
 * Class for handling the user interface of the library system.
 * 
 */
public class LibraryUI {
    private final LibraryManagement libraryManagement;
    private final UserManagement userManagement;
    private String currentUserId = null;

    /**
     * Constructor for the LibraryUI class.
     * 
     * @param libraryManagement
     * @param userManagement
     */
    public LibraryUI(LibraryManagement libraryManagement, UserManagement userManagement) {
        this.libraryManagement = libraryManagement;
        this.userManagement = userManagement;
    }

    /**
     * Main method to start the library system.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        UserManagement userManagement = new UserManagement();
        LibraryManagement libraryManagement = new LibraryManagement(userManagement);
        LibraryUI libraryUI = new LibraryUI(libraryManagement, userManagement);
        libraryUI.start();
    }

    /**
     * Start method to display the menu and handle user input.
     * 
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            
            System.out.println("\nMenü des Bibliothekssystems:\n");
            System.out.println("Aktueller Benutzer: " + (currentUserId != null ? userManagement.users.get(currentUserId).getName() : "Nicht angemeldet") + "\n");

            System.out.println("1. Anmelden");
            System.out.println("2. Medien durchsuchen");
            System.out.println("3. Gegenstand ausleihen"); 
            System.out.println("4. Gegenstand zurückgeben");
            System.out.println("5. Ausgeliehene Gegenstände und Gebühren anzeigen");
            System.out.println("6. Leihfrist verlängern");
            System.out.println("7. Überfällige Gebühren verbuchen (nur Admin)");
            System.err.println("8. Abmelden");
            System.out.println("0. Beenden\n");

            System.out.print("Wählen Sie eine Option: ");
            int option;
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                option = -1;
            }

            try {
                switch (option) {
                    case 1 -> login(scanner);
                    case 2 -> searchMedia(scanner);
                    case 3 -> borrowMedia(scanner);
                    case 4 -> returnMedia(scanner);
                    case 5 -> displayBorrowedItems();
                    case 6 -> extendLoan(scanner);
                    case 7 -> processUserPayment(scanner);
                    case 8 -> logout();
                    case 0 -> {
                        System.out.println("Das System wird beendet. Auf Wiedersehen!");
                        running = false;
                    }
                    default -> System.out.println("Ungültige Option. Bitte versuchen Sie es erneut.");
                }
            } catch (Exception e) {
                System.out.println("Fehler: " + e.getMessage());
            }
        }
    }

    private void login(Scanner scanner) {

        if (currentUserId != null) {
            System.out.println(userManagement.users.get(currentUserId).getName() + " ist bereits angemeldet.");
            return;
        }

        System.out.print("Geben Sie Ihre Benutzer-ID ein: ");
        String userId = scanner.nextLine();

        if (userManagement.users.containsKey(userId)) {
            currentUserId = userId;
            System.out.println("Angemeldet als Benutzer: " + userManagement.users.get(userId).getName());
        } else {
            System.out.println("Benutzer-ID nicht gefunden. Möchten Sie sich registrieren? (ja/nein)");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("ja")) {
                System.out.print("Geben Sie Ihren Namen ein: ");
                String name = scanner.nextLine();
                System.out.print("Geben Sie den Benutzertyp ein (student/erwachsener/admin): ");
                String userType = scanner.nextLine().trim().toLowerCase();
                
                try {
                    String newUserId = userManagement.createUser(userType, name);
                    System.out.println("Registrierung erfolgreich. Ihre Benutzer-ID ist: " + newUserId);
                    currentUserId = newUserId;
                } catch (Exception e) {
                    throw new InvalidUserRegistrationException("Fehler bei der Registrierung: " + e.getMessage());
                }
            } else {
                System.out.println("Anmeldung abgebrochen. Bitte versuchen Sie es erneut.");
            }
        }
    }

    private void logout() {
        if (currentUserId != null) {
            System.out.println("Benutzer " + currentUserId + " wurde abgemeldet.");
            currentUserId = null;
        } else {
            System.out.println("Kein Benutzer ist angemeldet.");
        }
    }

    private void searchMedia(Scanner scanner) {
        String titlePart;
        System.out.print("Geben Sie den Titels ein, nach dem gesucht werden soll: ");
        try {
            titlePart = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Fehler bei der Eingabe des Titels. Bitte versuchen Sie es erneut.");
            return;
        }
        System.out.println("\nSuchergebnisse:");
        List<String> media =libraryManagement.searchMedia(titlePart);

        if (media.isEmpty()) {
            System.out.println("Keine Medien gefunden.");
        } else {
            media.forEach(System.out::println);
        }
    }

    private void borrowMedia(Scanner scanner) {
        checkAnmeldung();
        System.out.print("Was soll ausgeliehen werden? Geben Sie die Medien-ID ein: ");
        String mediaId = scanner.nextLine();

        try {
            boolean success = libraryManagement.borrowMedia(currentUserId, mediaId);
            if (success) {
                System.out.println("Medium wurde erfolgreich ausgeliehen.");
            } else {
                System.out.println("Das Medium konnte nicht ausgeliehen werden.");
            }
        } catch (MediaUnavailableException e) {
            System.out.println("Fehler: " + e.getMessage());
        } catch (UserNotFoundException e) {
            System.out.println("Fehler: Benutzer nicht gefunden.");
        }
    }

    private void returnMedia(Scanner scanner) {
        checkAnmeldung();
        System.out.print("Was soll zurückgegeben werden? Geben Sie die Medien-ID ein: ");
        String mediaId = scanner.nextLine();

        try {
            double fee = libraryManagement.returnMedia(currentUserId, mediaId);
            if (fee > 0) {
                System.out.println("Medium wurde verspätet zurückgegeben. Gebühren: " + fee + " €");
            } else {
                System.out.println("Medium wurde erfolgreich und pünktlich zurückgegeben.");
            }
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void displayBorrowedItems() {
        checkAnmeldung();

        try {
            List<String> userInfo = libraryManagement.getUserFeesAndBorrowedItems(currentUserId);
            userInfo.forEach(System.out::println);
        } catch (UserNotFoundException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void extendLoan(Scanner scanner) {
        checkAnmeldung();
        System.out.print("Geben Sie die Medien-ID ein, für die die Leihfrist verlängert werden soll: ");
        String mediaId = scanner.nextLine();

        try {
            boolean success = libraryManagement.extendLoan(currentUserId, mediaId);
            if (success) {
                System.out.println("Die Leihfrist des Mediums wurde erfolgreich verlängert.");
            } else {
                System.out.println("Das Medium wurde nicht vom Benutzer ausgeliehen.");
            }
        } catch (RuntimeException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void processUserPayment(Scanner scanner) {

        if (!isAdmin(currentUserId)) {
            System.out.println("Zugriff verweigert. Sie sind kein Administrator.");
            return;
        }

        System.out.print("Geben Sie die Benutzer-ID ein, für die Sie die Zahlung verbuchen möchten: ");
        String userId = scanner.nextLine();
        System.out.print("Geben Sie den Betrag ein, den Sie verbuchen möchten: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {
            List<String> paymentInfo = libraryManagement.processPayment(userId, amount);
            paymentInfo.forEach(System.out::println);
        } catch (UserNotFoundException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private boolean isAdmin(String userId) {
        User user = userManagement.users.get(userId);
        return user != null && user instanceof Admin;
    }
    private void checkAnmeldung() {
        if (currentUserId == null) {
            throw new UserNotLoggedInException("Bitte melden Sie sich zuerst an.");
        }
    }
}

/**
 * This class is responsible for exceptions when the user registration is invalid.
 *
 * @return InvalidUserRegistrationException
 * @throws RuntimeException
 */
class InvalidUserRegistrationException extends RuntimeException {
    public InvalidUserRegistrationException(String message) {
        super(message);
    }
}

/**
 * This class is responsible for exceptions when the user is not logged in.
 *
 * @return UserNotLoggedInException
 * @throws RuntimeException
 */
class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String message) {
        super(message);
    }
}

/**
 * This class is responsible for exceptions when the user is not found.
 *
 * @return UserNotFoundException
 * @throws RuntimeException
 */
class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

/**
 * This class is responsible for exceptions when the media is not available.
 *
 * @return MediaUnavailableException
 * @throws RuntimeException
 */
class MediaUnavailableException extends RuntimeException {
    public MediaUnavailableException(String message) {
        super(message);
    }
}