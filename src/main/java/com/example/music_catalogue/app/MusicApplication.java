package com.example.music_catalogue.app;

import com.example.music_catalogue.factory.AuthWindowFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = AuthWindowFactory.createLoginScene();
        stage.setTitle("My Music Catalogue — Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
