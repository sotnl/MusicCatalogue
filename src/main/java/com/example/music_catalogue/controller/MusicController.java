package com.example.music_catalogue.controller;

import com.example.music_catalogue.model.Genre;
import com.example.music_catalogue.model.Song;
import com.example.music_catalogue.repository.GenreRepository;
import com.example.music_catalogue.repository.SongRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.sql.SQLException;

public class MusicController {
    private static final int PAGE_SIZE = 10;

    @FXML private TextField       idField;
    @FXML private TextField       titleField;
    @FXML private TextField       artistField;
    @FXML private TextField       albumField;
    @FXML private ComboBox<Genre> genreComboBox;
    @FXML private TextField       yearField;
    @FXML private ComboBox<Integer> ratingComboBox;
    @FXML private TextArea        notesArea;

    @FXML private TableView<Song>             songTable;
    @FXML private TableColumn<Song, Integer>  idColumn;
    @FXML private TableColumn<Song, String>   titleColumn;
    @FXML private TableColumn<Song, String>   artistColumn;
    @FXML private TableColumn<Song, String>   albumColumn;
    @FXML private TableColumn<Song, String>   genreColumn;
    @FXML private TableColumn<Song, Integer>  yearColumn;
    @FXML private TableColumn<Song, Integer>  ratingColumn;
    @FXML private TableColumn<Song, String>   notesColumn;

    @FXML private Pagination songPagination;

    private final SongRepository  songRepository  = new SongRepository();
    private final GenreRepository genreRepository = new GenreRepository();
    private final ObservableList<Song>    songs   = FXCollections.observableArrayList();
    private final ObservableList<Genre>   genres  = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        songTable.setItems(songs);
        genreComboBox.setItems(genres);
        ratingComboBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));

        songTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, selected) -> populateForm(selected));

        songPagination.setPageFactory(this::createPage);
        songPagination.currentPageIndexProperty().addListener((obs, oldIdx, newIdx) -> {
            try {
                loadPage(newIdx.intValue());
            } catch (SQLException e) {
                showError("Database error", e.getMessage());
            }
        });

        loadGenres();
        refreshPagination();
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        if (!validateForm(true)) return;
        try {
            songRepository.insert(buildSong(false));
            clearForm();
            refreshPagination();
        } catch (Exception e) {
            showError("Unable to add song", e.getMessage());
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if (!validateForm(false)) return;
        try {
            songRepository.update(buildSong(true));
            clearForm();
            refreshPagination();
        } catch (Exception e) {
            showError("Unable to update song", e.getMessage());
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Song selected = songTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("No selection", "Choose a song to delete.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete \"" + selected.getTitle() + "\" by " + selected.getArtist() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                try {
                    songRepository.delete(selected.getId());
                    clearForm();
                    refreshPagination();
                } catch (Exception e) {
                    showError("Unable to delete song", e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clearForm();
    }


    private void refreshPagination() {
        try {
            int total     = songRepository.countAll();
            int pageCount = Math.max(1, (int) Math.ceil(total / (double) PAGE_SIZE));
            songPagination.setPageCount(pageCount);
            int pageIndex = Math.min(songPagination.getCurrentPageIndex(), pageCount - 1);
            songPagination.setCurrentPageIndex(pageIndex);
            loadPage(pageIndex);
        } catch (SQLException e) {
            showError("Database error", e.getMessage());
        }
    }

    private javafx.scene.Node createPage(int pageIndex) {
        return new Region();
    }

    private void loadPage(int pageIndex) throws SQLException {
        int offset = pageIndex * PAGE_SIZE;
        songs.setAll(songRepository.findPage(PAGE_SIZE, offset));
        songTable.getSelectionModel().clearSelection();
    }

    private Song buildSong(boolean includeId) {
        Song song = new Song();
        if (includeId) {
            song.setId(Integer.parseInt(idField.getText().trim()));
        }
        song.setTitle(titleField.getText().trim());
        song.setArtist(artistField.getText().trim());
        song.setAlbum(albumField.getText().trim());
        song.setGenre(genreComboBox.getValue().getCode());
        song.setReleaseYear(Integer.parseInt(yearField.getText().trim()));
        song.setRating(ratingComboBox.getValue());
        song.setNotes(notesArea.getText().trim());
        return song;
    }

    private boolean validateForm(boolean allowEmptyId) {
        if (!allowEmptyId && idField.getText().trim().isEmpty()) {
            showError("Missing selection", "Select a song from the table first.");
            return false;
        }
        if (titleField.getText().trim().isEmpty()
                || artistField.getText().trim().isEmpty()
                || genreComboBox.getValue() == null
                || yearField.getText().trim().isEmpty()
                || ratingComboBox.getValue() == null) {
            showError("Missing data", "Title, artist, genre, year, and rating are required.");
            return false;
        }
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 1900 || year > 2100) {
                showError("Invalid year", "Enter a valid release year (1900–2100).");
                return false;
            }
            if (!allowEmptyId) {
                Integer.parseInt(idField.getText().trim());
            }
        } catch (NumberFormatException e) {
            showError("Invalid number", "ID and year must be numeric.");
            return false;
        }
        return true;
    }

    private void populateForm(Song song) {
        if (song == null) return;
        idField.setText(String.valueOf(song.getId()));
        titleField.setText(song.getTitle());
        artistField.setText(song.getArtist());
        albumField.setText(song.getAlbum());
        genreComboBox.getItems().stream()
                .filter(g -> g.getCode().equals(song.getGenre()))
                .findFirst()
                .ifPresent(genreComboBox::setValue);
        yearField.setText(String.valueOf(song.getReleaseYear()));
        ratingComboBox.setValue(song.getRating());
        notesArea.setText(song.getNotes() != null ? song.getNotes() : "");
    }

    private void clearForm() {
        idField.clear();
        titleField.clear();
        artistField.clear();
        albumField.clear();
        genreComboBox.setValue(null);
        yearField.clear();
        ratingComboBox.setValue(null);
        notesArea.clear();
        songTable.getSelectionModel().clearSelection();
    }

    private void loadGenres() {
        try {
            genres.setAll(genreRepository.findAll());
        } catch (SQLException e) {
            showError("Database error", e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
