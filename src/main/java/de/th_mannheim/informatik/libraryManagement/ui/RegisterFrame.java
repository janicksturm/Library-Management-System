package de.th_mannheim.informatik.libraryManagement.ui;

import de.th_mannheim.informatik.libraryManagement.management.UserManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.logging.Logger;

/**
 * RegisterFrame class for handling user registration in the Library Management System.
 * This frame includes fields for username and password, and buttons for registration, login, and exit.
 */
public class RegisterFrame extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(RegisterFrame.class.getName());
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private static final UserManagement userManagement = new UserManagement();

    /**
     * Constructor for RegisterFrame.
     * Initializes the frame with components for user registration.
     */
    public RegisterFrame() {
        super("Library Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton regBtn = new JButton("Register");
        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(regBtn);
        buttons.add(loginBtn);
        buttons.add(exitBtn);
        panel.add(buttons, gbc);

        regBtn.addActionListener(e -> registerUI());
        loginBtn.addActionListener(e -> openLoginUI());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void registerUI() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (userManagement.createUser(username, password, email)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Please Login", "Success", JOptionPane.INFORMATION_MESSAGE);
            LOGGER.info("User " + username + " registered successfully.");
            openLoginUI();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.warning("Failed to register user: " + username);
        }
    }

    private void openLoginUI() {
        LoginFrame ui = new LoginFrame();
        ui.setVisible(true);
        this.dispose();
    }
}
