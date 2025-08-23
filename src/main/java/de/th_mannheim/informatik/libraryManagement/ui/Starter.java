package de.th_mannheim.informatik.libraryManagement.ui;

import javax.swing.*;

/**
 * Starter class to launch the Library Management System application.
 * It sets the Nimbus look and feel and opens the LoginFrame.
 */
public class Starter {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Starter.class.getName());

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
