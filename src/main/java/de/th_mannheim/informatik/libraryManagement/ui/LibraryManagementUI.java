package de.th_mannheim.informatik.libraryManagement.ui;

import de.th_mannheim.informatik.libraryManagement.management.UserManagement;

import java.awt.*;
import javax.swing.*;

/**
 * Main UI class for the Library Management System.
 * This class sets up the main window, toolbars, and tabs for managing books, members, and loans.
 */
public class LibraryManagementUI extends JFrame {
    private final BookPanel booksPanel = new BookPanel();
    private final MemberPanel membersPanel = new MemberPanel();
    private final LoanPanel loansPanel = new LoanPanel();
    private static final UserManagement userManagement = new UserManagement();

    public LibraryManagementUI() {
        super("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));

        setJMenuBar(createMenuBar());
        add(createMainTabs(), BorderLayout.CENTER);
    }

    private JTabbedPane createMainTabs() {
        JTabbedPane tabs = new JTabbedPane();
        if (userManagement.getRole().equals("ADMIN")) {
            tabs.addTab("Books", booksPanel);
            tabs.addTab("Members", membersPanel);
            tabs.addTab("Loans", loansPanel);
        } else {
            tabs.addTab("Books", booksPanel);
            tabs.addTab("Loans", loansPanel);
        }
        return tabs;
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> logout());

        bar.add(logout);
        return bar;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        }
    }
}