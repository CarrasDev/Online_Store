package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.PedidoController;
import com.sqlsquad.onlinestore.modelo.DTO.PedidoDTO;
import com.sqlsquad.onlinestore.modelo.entity.Pedido;
import com.sqlsquad.onlinestore.modelo.enums.TipoEstado;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    private ObservableList<PedidoDTO> listaPedidos;
    private FilteredList<PedidoDTO> pedidosFiltrados;

    @FXML
    private void initialize() {
        // Obtenemos controlador de la capa de servicio
        pedidoController = AppService.getInstance().getPedidoController();

        filtroTipoEstado.getItems().addAll("Todos", "Pendiente", "Enviado");
        filtroTipoEstado.setValue("Todos"); // Por defecto Todos los pedidos

        // Inicializar listas
        listaPedidos = FXCollections.observableArrayList();
        pedidosFiltrados = new FilteredList<>(listaPedidos, p -> true);

        // Asignar FilteredList a la tabla
        tablaPedidos.setItems(pedidosFiltrados);

        // Eventos de filtrado
        filtroTipoEstado.setOnAction(e -> filtrarPedidos());
        emailFiltro.textProperty().addListener((observable, oldValue, newValue)
                -> filtrarPedidos());

        cargarPedidos();

        // Vincular las columnas
        colNumeroPedido.setCellValueFactory(cellData -> cellData.getValue().numeroPedidoProperty().asObject());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailClienteProperty());
        colArticulo.setCellValueFactory(cellData -> cellData.getValue().codigoArticuloProperty());
        colCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadArticulosProperty().asObject());
        colPrecioTotal.setCellValueFactory(cellData -> cellData.getValue().precioTotalProperty().asObject());
        colFechaPedido.setCellValueFactory(cellData -> cellData.getValue().fechaPedidoProperty());
        colTipoEstado.setCellValueFactory(cellData -> cellData.getValue().tipoEstadoProperty());


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
        // Limpiar la lista de pedidos y actualizarla Pedidos en BBDD
        listaPedidos.clear();
        pedidoController.actualizarPedidos();

        //convertir cada cliente en un ClienteDTO
        for (Pedido pedido : pedidoController.getListaPedidos()) {
            listaPedidos.add(new PedidoDTO(pedido));
        }
    }

    private void filtrarPedidos() {
        String filtroEstado = filtroTipoEstado.getValue().toLowerCase();
        String filtroEmail = emailFiltro.getText().toLowerCase();

        pedidosFiltrados.setPredicate(pedidoDTO -> {
            boolean coincideEstado = filtroEstado.equals("todos")
                    || filtroEstado.equals(pedidoDTO.tipoEstadoProperty().get().toLowerCase());
            boolean coincideEmail = filtroEmail.isEmpty()
                    || filtroEmail.equals(pedidoDTO.emailClienteProperty().get().toLowerCase());
            return coincideEstado && coincideEmail;
        });
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
            } else {
                mostrarError("No se pudo eliminar el pedido");
            }
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Eliminar Pedido...");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
