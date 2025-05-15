package com.sqlsquad.onlinestore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainAPP extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // TODO Integrar conexión BBBDD

        FXMLLoader fxmlLoader = new FXMLLoader(MainAPP.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        stage.setTitle("SQLSquad Online Store - JavaFX");
        stage.setScene(scene);
        stage.show();

        // TODO Integrar cierre BBDD

    }

    public static void main(String[] args) {
        launch();
    }
}