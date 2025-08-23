package de.th_mannheim.informatik.libraryManagement.ui;

import de.th_mannheim.informatik.libraryManagement.management.BookManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * AddBookFrame class for adding new books to the Library Management System.
 * This frame provides a user interface for entering book details.
 */
public class AddBookFrame extends JFrame {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private BookManagement bookManagement = new BookManagement();

    /**
     * Constructor for AddBookFrame.
     * Initializes the frame with components for adding a new book.
     */
    public AddBookFrame() {
        super("Add Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        isbnField = new JTextField(20);
        panel.add(isbnField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        titleField = new JTextField(20);
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        authorField = new JTextField(20);
        panel.add(authorField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        yearField = new JTextField(20);
        panel.add(yearField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addBtn);
        buttons.add(cancelBtn);
        panel.add(buttons, gbc);
        cancelBtn.addActionListener(e -> this.dispose());

        addBtn.addActionListener(e -> {
            if (bookManagement.checkIfBookExists(Long.parseLong(isbnField.getText().trim()))) {
                JOptionPane.showMessageDialog(this, "A book with this ISBN already exists.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            addBook();
        });
    }

    private void addBook() {
        try {
            long isbn = Long.parseLong(isbnField.getText().trim());
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());

            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Author cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bookManagement.addBooks(isbn, title, author, year);
            JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check ISBN and Year fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
