package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.ArticuloController;
import com.sqlsquad.onlinestore.modelo.DTO.ArticuloDTO;
import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LookArticulos {

    @FXML private TableView<ArticuloDTO> tablaArticulos;
    @FXML private TableColumn<ArticuloDTO, String> colCodigo;
    @FXML private TableColumn<ArticuloDTO, String> colDescripcion;
    @FXML private TableColumn<ArticuloDTO, Float> colPrecio;
    @FXML private TableColumn<ArticuloDTO, Float> colGastos;
    @FXML private TableColumn<ArticuloDTO, Integer> colTiempo;

    private ArticuloController articuloController;

    @FXML
    public void initialize() {
        // Obtenemos el controlador de la capa de servicios
        articuloController = AppService.getInstance().getArticuloController();

        // Cargar Artículos de la BBDD
        cargarArticulos();
    }

    @FXML
    private void cargarArticulos() {
        ObservableList<ArticuloDTO> listaArticulos = FXCollections.observableArrayList();

        // Convertir cada Articulo en ArticuloDTO
        for (Articulo articulo : articuloController.getListaArticulos()) {
            listaArticulos.add(new ArticuloDTO(articulo));
        }

        tablaArticulos.setItems(listaArticulos);

        // Vincular propiedades observables con la tabla
        colCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoArticuloProperty());
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioVentaProperty().asObject());
        colGastos.setCellValueFactory(cellData -> cellData.getValue().gastosEnvioProperty().asObject());
        colTiempo.setCellValueFactory(cellData -> cellData.getValue().tiempoPreparacionProperty().asObject());
    }
}
