package com.example.music_catalogue.repository;

import com.example.music_catalogue.model.Genre;
import com.example.music_catalogue.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {

    public List<Genre> findAll() throws SQLException {
        String sql = "SELECT code, label FROM genres ORDER BY code";
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                genres.add(new Genre(rs.getString("code"), rs.getString("label")));
            }
        }
        return genres;
    }
}
