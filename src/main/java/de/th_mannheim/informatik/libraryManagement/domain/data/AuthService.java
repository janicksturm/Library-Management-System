package de.th_mannheim.informatik.libraryManagement.domain.data;

import java.sql.*;
import java.util.logging.Logger;

public class AuthService {
    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static String authenticate(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT password, role FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String pw = rs.getString("password");
                String role = rs.getString("role");

                if (password.equals(pw)) {
                    return role;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Database error during authentication: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.severe("Unexpected error during authentication: " + e.getMessage());
        }
        return null;
    }
}
