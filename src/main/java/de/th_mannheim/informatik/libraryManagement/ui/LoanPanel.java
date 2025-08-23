package de.th_mannheim.informatik.libraryManagement.ui;

import de.th_mannheim.informatik.libraryManagement.management.UserManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * LoanPanel class for displaying and managing loans in the Library Management System.
 * This panel includes a table for loan records, a search field, and action buttons.
 */
public class LoanPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField searchField = new JTextField();
    private static final UserManagement userManagement = new UserManagement();

    public LoanPanel() {
        super(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(12, 12, 12, 12));

        model = new DefaultTableModel(new String[]{"Loan ID", "Book Title", "Member", "Due", "Status"}, 0) {
            @Override public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(model);

        JPanel top = new JPanel(new BorderLayout(8, 8));
        JPanel search = new JPanel(new BorderLayout(6, 6));
        search.add(new JLabel("Search:"), BorderLayout.WEST);
        search.add(searchField, BorderLayout.CENTER);
        JButton clear = new JButton("Clear");
        search.add(clear, BorderLayout.EAST);
        top.add(search, BorderLayout.CENTER);

        clear.addActionListener(e -> searchField.setText(""));

        if (userManagement.getRole().equals("ADMIN")) {
            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton add = new JButton("Add");
            JButton edit = new JButton("Edit");
            JButton del = new JButton("Delete");
            actions.add(add); actions.add(edit); actions.add(del);
            add(actions, BorderLayout.SOUTH);
        }

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}