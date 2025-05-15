package com.sqlsquad.onlinestore;

import com.sqlsquad.onlinestore.util.AppService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainAPP extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Inicializar la capa de servicios
        AppService.getInstance();

        // Inicializar la vista principal
        FXMLLoader fxmlLoader = new FXMLLoader(MainAPP.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 500);
        stage.setTitle("SQLSquad Online Store - JavaFX");
        stage.setScene(scene);
        // Asegurar cierre de la BBDD al cerrar la ventana principal
        stage.setOnCloseRequest(event -> AppService.getInstance().cerrarBBDD());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}