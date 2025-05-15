package com.sqlsquad.onlinestore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainViewController {

    @FXML
    private AnchorPane viewPane;  // Contenedor para la vista dinámica

    @FXML
    private AnchorPane detailsPane;  // Área para información adicional (se puede actualizar de forma similar)

    // Metodo de inicialización: se invoca cuando se carga el FXML
    @FXML
    public void initialize() {
        // Cargamos una vista predeterminada, por ejemplo, la vista de Artículos. // TODO Si hay tiempo una vista de presentación
        loadView("articulos.fxml");
    }

    // Maneja el clic en el botón "Artículos"
    @FXML
    private void handleArticulos(ActionEvent event) {
        loadView("articulos.fxml");
    }

    @FXML
    private void handleLookArticulos(ActionEvent event) {
        loadView("look-articulos.fxml");
    }

    // Maneja el clic en el botón "Clientes"
    @FXML
    private void handleClientes(ActionEvent event) {
        loadView("clientes.fxml");
    }

    // Maneja el clic en el botón "Pedidos"
    @FXML
    private void handlePedidos(ActionEvent event) {
        loadView("pedidos.fxml");
    }

    /**
     * Metodo para cargar una vista FXML en el contenedor 'viewPane'
     * @param fxmlFile El nombre del archivo FXML a cargar
     */
    private void loadView(String fxmlFile) {
        try {
            // Cargar el contenido de la vista
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            // Limpiar el contenedor (viewPane) y agregar la nueva vista
            viewPane.getChildren().clear();
            viewPane.getChildren().add(view);
            // Aseguramos que la vista se redimensione (anclándola a los 4 lados)
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}