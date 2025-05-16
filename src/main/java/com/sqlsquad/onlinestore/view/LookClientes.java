package com.sqlsquad.onlinestore.view;

import com.sqlsquad.onlinestore.controlador.ClienteController;
import com.sqlsquad.onlinestore.modelo.DTO.ClienteDTO;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.util.AppService;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LookClientes {

    // TODO @FXML de tab
    @FXML private TableView<ClienteDTO> tablaClientes;
    @FXML private TableColumn<ClienteDTO, String> colEmail;
    @FXML private TableColumn<ClienteDTO, String> colNombre;
    @FXML private TableColumn<ClienteDTO, String> colNif;
    @FXML private TableColumn<ClienteDTO, String> colDomicilio;
    @FXML private TableColumn<ClienteDTO, Float> colCuotaAnual;
    @FXML private TableColumn<ClienteDTO, Integer> colDescuento;
    @FXML private TableColumn<ClienteDTO, String> colTipoCliente;

    private ClienteController clienteController;

    @FXML
    public void initialize() {
        // Obtenemos el controlador de la capa de servicio
        clienteController = AppService.getInstance().getClienteController();

        // Cargar clientes de la BBDD
        // TODO buscar forma de mostrar en vista: Todos, Estandar y Premium
        // cargarClientes();
    }

    @FXML
    private void cargarClientes() {
        Observable<ClienteDTO> listaClientes = FXCollections.observableArrayList();

        // Convertir cada cliente en un ClienteDTO
        for (Cliente cliente : clienteController.getListaClientes()) {
            listaClientes.add(new clienteDTO(cliente));
        }

        tablaClientes.setItem(listaClientes);

        // TODO Vincular propiedades observables con la tabla


    }
}
