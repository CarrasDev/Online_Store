package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LookArticulos {

    @FXML private TableView<Articulo> tablaArticulos;
    @FXML private TableColumn<Articulo, String> colCodigo;
    @FXML private TableColumn<Articulo, String> colDescripcion;
    @FXML private TableColumn<Articulo, Float> colPrecio;
    @FXML private TableColumn<Articulo, Float> colGastos;
    @FXML private TableColumn<Articulo, Integer> colTiempo;

    @FXML
    public void initialize() {
        // Vinculamos las columnas a la entidad Articulo
        //colCodigo.setCellValueFactory(cellData -> cellData.getValue());
    }

}
