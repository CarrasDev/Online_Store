package modelo;

// Grupo 2 - SQL SQUAD


import DAO.FactoryDAO;
import DAO.IDao;
import modelo.entity.Articulo;
import modelo.entity.Pedido;
import modelo.entity.cliente.Cliente;
import modelo.enums.TipoEstado;

import java.util.*;


public class Modelo {

    //Añaden a listados
    public void addArticulo(Articulo articulo) {
        IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
        articuloDAO.save(articulo);
    }

    public void addPedido(Pedido pedido) {
        IDao<Pedido> pedidoDAO = FactoryDAO.getIDAO("PEDIDO");
        pedidoDAO.save(pedido);
    }

    public void addCliente(Cliente cliente) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        clienteDAO.save(cliente);
    }

    //Getters individuales
    public Cliente getCliente(String email) {
        IDao<Cliente> clienteDAO = FactoryDAO.getIDAO("CLIENTE");
        return clienteDAO.getById(email).orElse(null);
    }

    public Pedido getPedido(Integer numeroPedido) {
        IDao<Pedido> pedidoDAO = FactoryDAO.getIDAO("PEDIDO");
        return pedidoDAO.getById(numeroPedido.toString()).orElse(null);
    }

    public Articulo getArticulo(String codigoArticulo) {
        IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
        return articuloDAO.getById(codigoArticulo).orElse(null);
    }

    //Getters de listados
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

    public ArrayList<Articulo> getArticulos() {
        IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
        Collection<Articulo> listaArticulos = articuloDAO.getAll();

        if (listaArticulos != null) {
            return (ArrayList<Articulo>) listaArticulos;
        } else {
            throw new IllegalStateException("Lista de artículos vacía");
        }
    }

    public ArrayList<Pedido> getPedidos() {
        IDao<Pedido> pedidoDAO = FactoryDAO.getIDAO("PEDIDO");
        Collection<Pedido> listaPedidos = pedidoDAO.getAll();

        if (listaPedidos != null) {
            return (ArrayList<Pedido>) listaPedidos;
        } else {
            throw new IllegalStateException("Lista de pedidos vacía");
        }
    }

    public boolean existeArticulo(String codigoArticulo) {
        IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
        Optional<Articulo> articulo = articuloDAO.getById(codigoArticulo);
        if (articulo.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void actualizarPedidos() {

        //Recuperamos todos los pedidos y solo actualizamos los que hayan cambiado de estado a enviado.
        // Obtener los pedidos
        IDao<Pedido> pedidoDAO = FactoryDAO.getIDAO("PEDIDO");
        Collection<Pedido> listaPedidos = pedidoDAO.getAll();

        // Recorrer pedidos 1 a 1 y actualizar solo los que requieran cambio.
        if (listaPedidos != null) {
            boolean control = false;
            for (Pedido pedido : listaPedidos) {
                control = pedido.actualizarEstadoPreparacion();
                if (control) {
                    pedidoDAO.update(pedido);
                    control = false;
                }
            }
        } else {
            throw new IllegalStateException("Lista de pedidos vacía");
        }
    }

    public boolean eliminarPedido(Integer numeroPedido) {

        // Verifica si el estado es compatible para eliminar y elimina.
        IDao<Pedido> pedidoDAO = FactoryDAO.getIDAO("PEDIDO");
        Optional<Pedido> pedido = pedidoDAO.getById(numeroPedido.toString());

        if (pedido.isPresent()) {
            Pedido pedidoFinal = pedido.get(); // Se rescata el pedido dentro de Optional
            pedidoFinal.actualizarEstadoPreparacion();
            if (pedidoFinal.getEstado().equals(TipoEstado.PENDIENTE)) {
                pedidoDAO.delete(pedidoFinal);
                return true;
            }
        } else {
            throw new IllegalStateException("Pedido no encontrado");
        }
        return false;
    }

    public String toString() {
        return "Tienda OnLineStore de SQL Squad";
    }
}
