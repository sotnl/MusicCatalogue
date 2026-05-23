package com.example.music_catalogue.repository;

import com.example.music_catalogue.model.Song;
import com.example.music_catalogue.util.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongRepository {

    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM songs";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        }
    }

    public List<Song> findPage(int limit, int offset) throws SQLException {
        String sql = "SELECT id, title, artist, album, genre, release_year, rating, notes "
                + "FROM songs ORDER BY id DESC LIMIT ? OFFSET ?";
        List<Song> songs = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    songs.add(map(rs));
                }
            }
        }
        return songs;
    }

    public List<Song> findAll() throws SQLException {
        String sql = "SELECT id, title, artist, album, genre, release_year, rating, notes "
                + "FROM songs ORDER BY id DESC";
        List<Song> songs = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                songs.add(map(rs));
            }
        }
        return songs;
    }

    public void insert(Song song) throws SQLException {
        String sql = "INSERT INTO songs (title, artist, album, genre, release_year, rating, notes) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        executeWrite(sql, song, false);
    }

    public void update(Song song) throws SQLException {
        String sql = "UPDATE songs SET title = ?, artist = ?, album = ?, genre = ?, "
                + "release_year = ?, rating = ?, notes = ? WHERE id = ?";
        executeWrite(sql, song, true);
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM songs WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }


    private Song map(ResultSet rs) throws SQLException {
        return new Song(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("artist"),
                rs.getString("album"),
                rs.getString("genre"),
                rs.getInt("release_year"),
                rs.getInt("rating"),
                rs.getString("notes")
        );
    }

    private void executeWrite(String sql, Song song, boolean includeId) throws SQLException {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, song.getTitle());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getAlbum());
            statement.setString(4, song.getGenre());
            statement.setInt(5, song.getReleaseYear());
            statement.setInt(6, song.getRating());
            statement.setString(7, song.getNotes());
            if (includeId) {
                statement.setInt(8, song.getId());
            }
            statement.executeUpdate();
        }
    }
}
