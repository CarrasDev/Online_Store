package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.ClienteController;
import com.sqlsquad.onlinestore.modelo.DTO.ClienteDTO;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClienteEstandar;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClientePremium;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LookClientes {

    @FXML private TableView<ClienteDTO> tablaClientes;
    @FXML private TableColumn<ClienteDTO, String> colEmail;
    @FXML private TableColumn<ClienteDTO, String> colNombre;
    @FXML private TableColumn<ClienteDTO, String> colNif;
    @FXML private TableColumn<ClienteDTO, String> colDomicilio;
    @FXML private TableColumn<ClienteDTO, Float> colCuotaAnual;
    @FXML private TableColumn<ClienteDTO, Integer> colDescuento;
    @FXML private TableColumn<ClienteDTO, String> colTipoCliente;

    @FXML private ComboBox<String> filtroTipoCliente;

    private ClienteController clienteController;

    @FXML
    public void initialize() {
        // Obtenemos el controlador de la capa de servicio
        clienteController = AppService.getInstance().getClienteController();

        filtroTipoCliente.getItems().addAll("Todos", "Premium", "Estandar");
        filtroTipoCliente.setValue("Todos"); // Por defecto Todos los clientes

        // Evento para cambiar el filtro
        filtroTipoCliente.setOnAction(e -> filtrarClientes());

        // Carga inicial
        cargarClientes();
    }


    @FXML
    private void cargarClientes() {
        ObservableList<ClienteDTO> listaClientes = FXCollections.observableArrayList();

        // Convertir cada cliente en un ClienteDTO
        for (Cliente cliente : clienteController.getListaClientes()) {
            listaClientes.add(new ClienteDTO(cliente));
        }

        // Actualizamos la tabla en la vista
        tablaClientes.setItems(listaClientes);

        // Vincular propiedades observables con la tabla
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colNif.setCellValueFactory(cellData -> cellData.getValue().nifProperty());
        colDomicilio.setCellValueFactory(cellData -> cellData.getValue().domicilioProperty());
        colCuotaAnual.setCellValueFactory(cellData -> cellData.getValue().cuotaAnualProperty().asObject());
        colDescuento.setCellValueFactory(cellData -> cellData.getValue().descuentoProperty().asObject());
        colTipoCliente.setCellValueFactory(cellData -> cellData.getValue().tipoClienteProperty());
    }

    private void filtrarClientes() {
        ObservableList<ClienteDTO> listaClientes = FXCollections.observableArrayList();

        String filtro = filtroTipoCliente.getValue();

        for (Cliente cliente : clienteController.getListaClientes()) {
            if (filtro.equals("Todos")
                    || (filtro.equals("Premium") && cliente instanceof ClientePremium)
                    || filtro.equals("Estandar") && cliente instanceof ClienteEstandar) {
                listaClientes.add(new ClienteDTO(cliente));
            }
        }
        tablaClientes.setItems(listaClientes);
    }

}
