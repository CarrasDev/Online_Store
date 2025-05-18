package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.PedidoController;
import com.sqlsquad.onlinestore.modelo.DTO.PedidoDTO;
import com.sqlsquad.onlinestore.modelo.entity.Pedido;
import com.sqlsquad.onlinestore.modelo.enums.TipoEstado;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDateTime;

public class LookPedidos {

    @FXML private TableView<PedidoDTO> tablaPedidos;
    @FXML private TableColumn<PedidoDTO, Integer> colNumeroPedido;
    @FXML private TableColumn<PedidoDTO, String> colEmail;
    @FXML private TableColumn<PedidoDTO, String> colArticulo;
    @FXML private TableColumn<PedidoDTO, Integer> colCantidad;
    @FXML private TableColumn<PedidoDTO, Double> colPrecioTotal;
    @FXML private TableColumn<PedidoDTO, String> colFechaPedido;
    @FXML private TableColumn<PedidoDTO, String> colTipoEstado;

    @FXML private ComboBox<String> filtroTipoEstado;

    private PedidoController pedidoController;

    @FXML
    private void initialize() {
        // Obtenemos controlador de la capa de servicio
        pedidoController = AppService.getInstance().getPedidoController();

        filtroTipoEstado.getItems().addAll("Todos", "Pendiente", "Enviado");
        filtroTipoEstado.setValue("Todos"); // Por defecto Todos los pedidos

        // Evento para cambiar el filtro
        filtroTipoEstado.setOnAction(e -> filtrarPedidos());

        // Carga inicial
        cargarPedidos();
    }

    @FXML
    private void cargarPedidos() {
        ObservableList<PedidoDTO> listaPedidos = FXCollections.observableArrayList();

        pedidoController.actualizarPedidos();

        //convertir cada cliente en un ClienteDTO
        for (Pedido pedido : pedidoController.getListaPedidos()) {
            listaPedidos.add(new PedidoDTO(pedido));
        }

        // Actualizamos la tabla en la vista
        tablaPedidos.setItems(listaPedidos);

        // TODO Vincular propiedades observables con la tabla
        colNumeroPedido.setCellValueFactory(cellData -> cellData.getValue().numeroPedidoProperty().asObject());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailClienteProperty());
        colArticulo.setCellValueFactory(cellData -> cellData.getValue().codigoArticuloProperty());
        colCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadArticulosProperty().asObject());
        colPrecioTotal.setCellValueFactory(cellData -> cellData.getValue().precioTotalProperty().asObject());
        colFechaPedido.setCellValueFactory(cellData -> cellData.getValue().fechaPedidoProperty());
        colTipoEstado.setCellValueFactory(cellData -> cellData.getValue().tipoEstadoProperty());
    }

    private void filtrarPedidos() {
        ObservableList<PedidoDTO> listaPedidos = FXCollections.observableArrayList();

        pedidoController.actualizarPedidos();

        String filtro = filtroTipoEstado.getValue();

        for (Pedido pedido : pedidoController.getListaPedidos()) {
            if (filtro.equals("Todos")
                    || (filtro.equals("Pendiente") && pedido.getTipoEstado().equals(TipoEstado.PENDIENTE))
                    || (filtro.equals("Enviado") && pedido.getTipoEstado().equals(TipoEstado.ENVIADO))) {
                listaPedidos.add(new PedidoDTO(pedido));
            }
        }
        tablaPedidos.setItems(listaPedidos);
    }
}
