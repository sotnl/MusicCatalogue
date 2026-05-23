package com.example.music_catalogue.repository;

import com.example.music_catalogue.util.Database;
import com.example.music_catalogue.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthRepository {

    public boolean authenticate(String username, String password) throws SQLException {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return PasswordUtil.verify(password, rs.getString("password_hash"));
                }
            }
        }
        return false;
    }

    public boolean register(String username, String password) throws SQLException {
        if (userExists(username)) {
            return false;
        }
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, PasswordUtil.hash(password));
            statement.executeUpdate();
        }
        return true;
    }

    private boolean userExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }
}
