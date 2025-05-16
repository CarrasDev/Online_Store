package com.sqlsquad.onlinestore.modelo;

import com.sqlsquad.onlinestore.DAO.FactoryDAO;
import com.sqlsquad.onlinestore.DAO.IDao;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;

import java.util.Collection;
import java.util.List;

public class ClienteModel {

    public void addCliente(Cliente cliente) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        clienteDAO.save(cliente);
    }

    public Cliente getCliente(String email) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        return clienteDAO.getById(email).orElse(null);
    }

    // TODO convertir a Lista NO HASHMAP
    public List<Cliente> getListaClientes() {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        Collection<Cliente> listaClientes = clienteDAO.getAll();
        return (List<Cliente>) listaClientes;
    }

    public boolean existeCliente(String email) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        return clienteDAO.getById(email).isPresent();
    }
}
