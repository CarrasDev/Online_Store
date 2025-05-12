package modelo;

import DAO.FactoryDAO;
import DAO.IDao;
import modelo.cliente.Cliente;

import java.util.Collection;
import java.util.HashMap;

public class ClienteModel {

    // TODO Desarrollo ClienteModel

    public void addCliente(Cliente cliente) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        clienteDAO.save(cliente);
    }

    public Cliente getCliente(String email) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        return clienteDAO.getById(email).orElse(null);
    }

    public HashMap<String, Cliente> getListaClientes() {
        // Recibimos un ArrayList de la BBDD y lo transformamos en HashMap
        HashMap<String, Cliente> clientes = new HashMap<>();

        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        Collection<Cliente> listaClientes = clienteDAO.getAll();

        if (listaClientes != null) {
            for (Cliente cliente : listaClientes) {
                clientes.put(cliente.getEmail(), cliente);
            }
        } else {
            throw new IllegalStateException("Lista de clientes vacía");
        }
        return clientes;
    }

    public boolean existeCliente(String email) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        return clienteDAO.getById(email).isPresent();
    }

    // TODO ¿Necesario un toString()?

}
