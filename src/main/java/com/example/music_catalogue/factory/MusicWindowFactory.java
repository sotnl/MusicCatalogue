package com.example.music_catalogue.factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public final class MusicWindowFactory {
    private MusicWindowFactory() {
    }

    public static Scene createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                MusicWindowFactory.class.getResource("/com/example/music_catalogue/music-view.fxml"));
        return new Scene(loader.load(), 1100, 700);
    }
}
