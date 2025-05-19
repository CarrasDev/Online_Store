package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.PedidoController;
import com.sqlsquad.onlinestore.modelo.DTO.PedidoDTO;
import com.sqlsquad.onlinestore.modelo.entity.Pedido;
import com.sqlsquad.onlinestore.modelo.enums.TipoEstado;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;


public class LookPedidos {

    @FXML private TableView<PedidoDTO> tablaPedidos;
    @FXML private TableColumn<PedidoDTO, Integer> colNumeroPedido;
    @FXML private TableColumn<PedidoDTO, String> colEmail;
    @FXML private TableColumn<PedidoDTO, String> colArticulo;
    @FXML private TableColumn<PedidoDTO, Integer> colCantidad;
    @FXML private TableColumn<PedidoDTO, Double> colPrecioTotal;
    @FXML private TableColumn<PedidoDTO, String> colFechaPedido;
    @FXML private TableColumn<PedidoDTO, String> colTipoEstado;

    @FXML private TextField emailFiltro;
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

        // Evento de menú contextual para eliminar Pedidos
        tablaPedidos.setRowFactory(tv -> {
            TableRow<PedidoDTO> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem eliminarItem = new MenuItem("Eliminar");
            eliminarItem.setOnAction(event -> {
                // Obtener el PedidoDTO de la fila seleccionada
                PedidoDTO pedidoSeleccionado = row.getItem();
                if (pedidoSeleccionado != null) {
                    // Emitimos un mensaje de alerta para asegurar eliminación
                    confirmarDelete(pedidoSeleccionado);
                }
            });

            // Agregamos la función eliminar al menú contextual
            contextMenu.getItems().add(eliminarItem);

            // Solo se muestra el menú en filas no vacías
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
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

    private void confirmarDelete(PedidoDTO pedidoSeleccionado) {
        // Mensaje emergente con confirmación de eliminación de pedido
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar Pedido...");
        alerta.setHeaderText("Va ha eliminar el pedido nº " + pedidoSeleccionado.numeroPedidoProperty().get());
        alerta.setContentText("¿Está seguro de querer eliminarlo?");

        // Definir botones de respuesta
        ButtonType botonSi = new ButtonType("Si");
        ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alerta.getButtonTypes().setAll(botonSi, botonNo);

        //Mostrar alerta y esperar respuesta del usuario
        Optional<ButtonType> resultado = alerta.showAndWait();

        if (resultado.isPresent() && resultado.get() == botonSi) {
            // Eliminar el registro de la BBDD
            boolean control = false;
            control = pedidoController.removePedido(pedidoSeleccionado.numeroPedidoProperty().get());
            if (control) {
                // Eliminar el registro del ObservableList
                tablaPedidos.getItems().remove(pedidoSeleccionado);
            }
        }
    }
}
