package src.Q7_social_media_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterLogic {
    private static final String DB_URL = "jdbc:mysql://your_database_url:3306/your_database_name";
    private static final String DB_USER = "your_database_username";
    private static final String DB_PASSWORD = "your_database_password";

    public static boolean performRegistration(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0; // true if registration successful, false otherwise
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
