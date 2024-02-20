package src.Q7_social_media_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginLogic {
    private static final String DB_URL = "jdbc:mysql://your_database_url:3306/social_media";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "T@ekwondo12";

    public static boolean validateLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // true if user found, false otherwise
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
