package com.sqlsquad.onlinestore.controlador;


import com.sqlsquad.onlinestore.modelo.*;
import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import com.sqlsquad.onlinestore.modelo.entity.Pedido;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.modelo.enums.TipoEstado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/// Controlador para la Gestión de Pedido

public class PedidoController {

    private final PedidoModel pedidoModel;
    private final ArticuloModel articuloModel;
    private final ClienteModel clienteModel;

    // Constructor
    public PedidoController(PedidoModel pedidoModel, ArticuloModel articuloModel,
                            ClienteModel clienteModel) {

        this.pedidoModel = pedidoModel;
        this.articuloModel = articuloModel;
        this.clienteModel = clienteModel;
    }

    //ACTUALIZA MODELO: Permite añadir un pedido al modelo.
    public void addPedido(String codigoArticulo, Integer cantidadArticulos, String emailCliente) {

        Articulo articulo = articuloModel.getArticulo(codigoArticulo);
        Cliente cliente = clienteModel.getCliente(emailCliente);
        Pedido pedido = new Pedido(articulo, cantidadArticulos, cliente, LocalDateTime.now(), TipoEstado.PENDIENTE);
        pedidoModel.addPedido(pedido);
    }

    public List<Pedido> getListaPedidos(){
        return pedidoModel.getPedidos();
    }

    public void actualizarPedidos(){
        pedidoModel.actualizarPedidos();
    }

    public boolean removePedido(Integer numeroPedido) {
        return pedidoModel.eliminarPedido(numeroPedido);
    }

    private Pedido getPedido(Integer numeroPedido) {
        return pedidoModel.getPedido(numeroPedido);
    }
}
