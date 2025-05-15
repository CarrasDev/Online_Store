package com.sqlsquad.onlinestore.controlador;


import com.sqlsquad.onlinestore.modelo.*;
import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import com.sqlsquad.onlinestore.modelo.entity.Pedido;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import modelo.enums.TipoEstado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/// Controlador para la Gestión de Pedido

public class PedidoController {

    // TODO variables --> Modificar uso de Vista según proceda
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
        if (codigoArticulo == null || codigoArticulo.isEmpty()) {
            // TODO vistaTienda.updateView("Error: Código de artículo inválido");
            return;
        }
        if (emailCliente == null || emailCliente.isEmpty()) {
            // TODO vistaTienda.updateView("Error: Email del cliente inválido");
            return;
        }
        Articulo articulo = articuloModel.getArticulo(codigoArticulo);
        if (articulo == null) {
            // TODO vistaTienda.updateView("Error: El artículo no existe");
            return;
        }
        Cliente cliente = clienteModel.getCliente(emailCliente);
        if (cliente == null) {
            // TODO vistaTienda.updateView("Error: El cliente no existe");
            return;
        }
        if (cantidadArticulos == null || cantidadArticulos <= 0) {
            // TODO vistaTienda.updateView("Error: La cantidad debe ser mayor que cero");
            return;
        }
        // Integer numeroPedido = modeloTienda.generarProximoPedido();
        Pedido pedido = new Pedido(articulo, cantidadArticulos, cliente, LocalDateTime.now(), TipoEstado.PENDIENTE);
        pedidoModel.addPedido(pedido);
        // TODO vistaTienda.updateView("Pedido añadido");
    }

    //ACTUALIZA MODELO:Permite eliminar un pedido del modelo.
    //Un pedido puede ser borrado únicamente si no ha sido enviado, es decir, si el tiempo transcurrido a desde
    // la fecha y hora del pedido no supera el tiempo de preparación para el envío del artículo.
    public void removePedido(Integer numeroPedido) {
        if(esBorrable(numeroPedido)){
            pedidoModel.eliminarPedido(numeroPedido);
            // TODO vistaTienda.updateView("Pedido eliminado \n");
        } else {
            // TODO vistaTienda.updateView("No es posible borrar el pedido \n");
        }
    }


//Métodos que toman info del modelo

    //Recupera pedido del modelo
    private <T> T getPedido(Integer numeroPedido) {
        return ((T) pedidoModel.getPedido(numeroPedido));
    }

    // Eliminar Pedido. Un pedido puede ser borrado únicamente si no ha sido enviado, es decir,
    // si el tiempo transcurrido a desde la fecha y hora del pedido no supera el tiempo de preparación
    // para el envío del artículo.
    private boolean esBorrable(int numeroPedido){
        Pedido pedido = getPedido(numeroPedido);
        return pedido.getEstado() != TipoEstado.ENVIADO;
    }

    private <E> List<E> getListaPedidos(){
        return (List<E>)pedidoModel.getPedidos();
    }

    //Llama al metodo correcto en base al emailCliente
    public void mostrarPedidosPendientes(String emailCliente) {
        if ("T".equalsIgnoreCase(emailCliente)){
            mostrarTodosLosPedidosPendientes();
        } else {
            if (!clienteModel.existeCliente(emailCliente)) {
                // TODO vistaTienda.updateView("El cliente con email '" + emailCliente + "' no está registrado.\n");
                return;
            }
            mostrarPedidoPendientesPorCliente(emailCliente);
        }
    }

    //ACTUALIZA VISTA: con listado de pedidos pendientes filtrando por cliente
    private void mostrarPedidoPendientesPorCliente(String emailCliente) {
        pedidoModel.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if ((Objects.equals(pedido.getCliente().getEmail(), emailCliente))&&(pedido.getEstado()==TipoEstado.PENDIENTE)){
                sb.append(pedido.toString()).append("\n");
            }
        }
        // TODO vistaTienda.updateView(sb.toString());
    }

    //ACTUALIZA VISTA: con listado de todos los pedidos pendientes
    private void mostrarTodosLosPedidosPendientes() {
        pedidoModel.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if (pedido.getEstado()==TipoEstado.PENDIENTE){
                sb.append(pedido.toString()).append("\n");
            }
        }
        // TODO vistaTienda.updateView(sb.toString());
    }

    //Llama al metodo correcto en base al emailCliente
    public void mostrarPedidosEnviados(String emailCliente) {
        pedidoModel.actualizarPedidos();
        if ("T".equalsIgnoreCase(emailCliente)){
            mostrarTodosLosPedidosEnviados();
        } else {
            if (!clienteModel.existeCliente(emailCliente)) {
                // TODO vistaTienda.updateView("El cliente con email '" + emailCliente + "' no está registrado.\n");
                return;
            }
            mostrarPedidoEnviadosPorCliente(emailCliente);
        }
    }

    //ACTUALIZA VISTA:Crea listado de los pedidos enviados filtrados por cliente actualizando la vista
    private void mostrarPedidoEnviadosPorCliente(String emailCliente) {
        pedidoModel.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if ((Objects.equals(pedido.getCliente().getEmail(), emailCliente))&&(pedido.getEstado()==TipoEstado.ENVIADO)){
                sb.append(pedido.toString()).append("\n");
            }
        }
        // TODO vistaTienda.updateView(sb.toString());
    }

    ////ACTUALIZA VISTA: Crea listado de todos los pedidos enviados actualizando la vista
    private void mostrarTodosLosPedidosEnviados() {
        pedidoModel.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if (pedido.getEstado()==TipoEstado.ENVIADO){
                sb.append("%s\n".formatted(pedido.toString()));
            }
        }
        // TODO vistaTienda.updateView(sb.toString());
    }

}
