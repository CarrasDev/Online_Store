package com.sqlsquad.onlinestore.controlador;

import com.sqlsquad.onlinestore.modelo.ClienteModel;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;

import java.util.List;

/// Controlador para la Gestión de Cliente

public class ClienteController {

    private final ClienteModel clienteModel;

    // Constructor
    public ClienteController(ClienteModel clienteModel) {
        this.clienteModel = clienteModel;
    }

    public void addCliente(Cliente cliente) {
        clienteModel.addCliente(cliente);
    }

    public List<Cliente> getListaClientes() {
        return clienteModel.getListaClientes();
    }

    public boolean existeCliente(String email) {
        return clienteModel.existeCliente(email);
    }
}
