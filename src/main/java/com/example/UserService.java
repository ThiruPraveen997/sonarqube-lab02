package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    // Constructor injection (easy to unit test)
    public UserService(String dbUrl, String dbUser, String dbPassword) {
        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new IllegalArgumentException(
                "Database configuration must not be null");
        }
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    // Default constructor for production
    public UserService() {
        this(
            System.getenv("DB_URL"),
            System.getenv("DB_USER"),
            System.getenv("DB_PASSWORD")
        );
    }

    public void findUser(String username) throws SQLException {

        String query = "SELECT id, name FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.executeQuery();
        }
    }

    public void deleteUser(String username) throws SQLException {

        String query = "DELETE FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);
            ps.executeUpdate();
        }
    }
}
