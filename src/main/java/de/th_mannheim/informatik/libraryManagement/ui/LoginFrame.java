package de.th_mannheim.informatik.libraryManagement.ui;

import de.th_mannheim.informatik.libraryManagement.domain.data.AuthService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.logging.Logger;

/**
 * LoginFrame class for handling user login in the Library Management System.
 * This frame includes fields for username and password, and buttons for login and exit.
 */
public class LoginFrame extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(LibraryManagementUI.class.getName());
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        super("Library Login");
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

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(loginBtn);
        buttons.add(exitBtn);
        panel.add(buttons, gbc);

        loginBtn.addActionListener(e -> login());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (AuthService.authenticate(username, password) != null) {
            String role = AuthService.authenticate(username, password);
            LOGGER.info("User " + username + " logged in with role: " + role);
            openMainUI(role);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid login", "Login Error", JOptionPane.ERROR_MESSAGE);
            LOGGER.warning("Failed login attempt for user: " + username);
        }
        usernameField.setText("");
        passwordField.setText("");
    }

    private void openMainUI(String role) {
        LibraryManagementUI ui = new LibraryManagementUI(role);
        ui.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to set Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}