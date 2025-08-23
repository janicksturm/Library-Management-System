package de.th_mannheim.informatik.libraryManagement.ui;

import de.th_mannheim.informatik.libraryManagement.management.BookManagement;
import de.th_mannheim.informatik.libraryManagement.management.UserManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * BookPanel class for displaying and managing books in the Library Management System.
 * This panel includes a table for book records, a search field, and action buttons.
 */
public class BookPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField searchField = new JTextField();
    private static final BookManagement bookManagement = new BookManagement();
    private static final UserManagement userManagement = new UserManagement();

    public BookPanel() {
        super(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        model = new DefaultTableModel(new String[]{"ID", "ISBN", "Title", "Author", "Year", "Status"}, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(model);

        bookManagement.displayBooks(model);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        JPanel search = new JPanel(new BorderLayout(6, 6));
        search.add(new JLabel("Search:"), BorderLayout.WEST);
        search.add(searchField, BorderLayout.CENTER);
        JButton clear = new JButton("Clear");
        search.add(clear, BorderLayout.EAST);
        top.add(search, BorderLayout.CENTER);

        clear.addActionListener(e -> searchField.setText(""));

        getPanel();

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void getPanel() {
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        if (userManagement.getRole().equals("ADMIN")) {
            JButton add = new JButton("Add");

            add.addActionListener(e -> {
                AddBookFrame addBookFrame = new AddBookFrame();
                addBookFrame.setVisible(true);
            });

            JButton edit = new JButton("Edit");
            JButton del = new JButton("Delete");
            actions.add(add); actions.add(edit); actions.add(del);
        }

        JButton update = new JButton("Update");

        update.addActionListener(e -> {
            model.setRowCount(0);
            bookManagement.displayBooks(model);
        });
        actions.add(update);
        add(actions, BorderLayout.SOUTH);
    }
}