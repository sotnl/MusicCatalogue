module com.example.music_catalogue {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.example.music_catalogue.app to javafx.graphics;
    opens com.example.music_catalogue.controller to javafx.fxml;
    opens com.example.music_catalogue.model to javafx.base;
}
