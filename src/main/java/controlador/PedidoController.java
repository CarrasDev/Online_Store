package controlador;


import modelo.Articulo;
import modelo.Pedido;
import modelo.cliente.Cliente;
import modelo.enums.TipoEstado;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/// Controlador para la Gestión de Pedido

public class PedidoController {

    // TODO Desarrollo PedidoController

    // TODO variables --> Modelo y Vista correspondiente

    // TODO Constructor

    //ACTUALIZA MODELO: Permite añadir un pedido al modelo.
    public void addPedido(String codigoArticulo, Integer cantidadArticulos, String emailCliente) {
        if (codigoArticulo == null || codigoArticulo.isEmpty()) {
            vistaTienda.updateView("Error: Código de artículo inválido");
            return;
        }
        if (emailCliente == null || emailCliente.isEmpty()) {
            vistaTienda.updateView("Error: Email del cliente inválido");
            return;
        }
        Articulo articulo = modeloTienda.getArticulo(codigoArticulo);
        if (articulo == null) {
            vistaTienda.updateView("Error: El artículo no existe");
            return;
        }
        Cliente cliente = modeloTienda.getCliente(emailCliente);
        if (cliente == null) {
            vistaTienda.updateView("Error: El cliente no existe");
            return;
        }
        if (cantidadArticulos == null || cantidadArticulos <= 0) {
            vistaTienda.updateView("Error: La cantidad debe ser mayor que cero");
            return;
        }
        // Integer numeroPedido = modeloTienda.generarProximoPedido();
        Pedido pedido = new Pedido(articulo, cantidadArticulos, cliente, LocalDateTime.now(), TipoEstado.PENDIENTE);
        modeloTienda.addPedido(pedido);
        vistaTienda.updateView("Pedido añadido");
    }
    //ACTUALIZA MODELO:Permite eliminar un pedido del modelo.
    //Un pedido puede ser borrado únicamente si no ha sido enviado, es decir, si el tiempo transcurrido a desde
    // la fecha y hora del pedido no supera el tiempo de preparación para el envío del artículo.
    public void removePedido(Integer numeroPedido) {
        if(esBorrable(numeroPedido)){
            modeloTienda.eliminarPedido(numeroPedido);
            vistaTienda.updateView("Pedido eliminado \n");
        } else {
            vistaTienda.updateView("No es posible borrar el pedido \n");
        }
    }


//Métodos que toman info del modelo

    //Recupera pedido del modelo
    private <T> T getPedido(Integer numeroPedido) {
        return ((T) modeloTienda.getPedido(numeroPedido));
    }

    // Eliminar Pedido. Un pedido puede ser borrado únicamente si no ha sido enviado, es decir,
    // si el tiempo transcurrido a desde la fecha y hora del pedido no supera el tiempo de preparación
    // para el envío del artículo.
    private boolean esBorrable(int numeroPedido){
        Pedido pedido = getPedido(numeroPedido);
        return pedido.getEstado() != TipoEstado.ENVIADO;
    }

    private <E> List<E> getListaPedidos(){
        return (List<E>)modeloTienda.getPedidos();
    }

    //Llama al metodo correcto en base al emailCliente
    public void mostrarPedidosPendientes(String iDcliente) {
        if (Objects.equals(iDcliente, "T")){
            mostrarTodosLosPedidosPendientes();
        } else {
            if (!esClienteRegistrado(iDcliente)) {
                vistaTienda.updateView("El cliente con email '" + iDcliente + "' no está registrado.\n");
                return;
            }
            mostrarPedidoPendientesPorCliente(iDcliente);
        }
    }

    //ACTUALIZA VISTA: con listado de pedidos pendientes filtrando por cliente
    private void mostrarPedidoPendientesPorCliente(String emailCliente) {
        modeloTienda.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if ((Objects.equals(pedido.getCliente().getEmail(), emailCliente))&&(pedido.getEstado()==TipoEstado.PENDIENTE)){
                sb.append(pedido.toString()).append("\n");
            }
        }
        vistaTienda.updateView(sb.toString());
    }

    //ACTUALIZA VISTA: con listado de todos los pedidos pendientes
    private void mostrarTodosLosPedidosPendientes() {
        modeloTienda.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if (pedido.getEstado()==TipoEstado.PENDIENTE){
                sb.append(pedido.toString()).append("\n");
            }
        }
        vistaTienda.updateView(sb.toString());
    }

    //Llama al metodo correcto en base al emailCliente
    public void mostrarPedidosEnviados(String emailCliente) {
        modeloTienda.actualizarPedidos();
        if (Objects.equals(emailCliente, "T")){
            mostrarTodosLosPedidosEnviados();
        } else {
            if (!esClienteRegistrado(emailCliente)) {
                vistaTienda.updateView("El cliente con email '" + emailCliente + "' no está registrado.\n");
                return;
            }
            mostrarPedidoEnviadosPorCliente(emailCliente);
        }
    }

    //ACTUALIZA VISTA:Crea listado de los pedidos enviados filtrados por cliente actualizando la vista
    private void mostrarPedidoEnviadosPorCliente(String emailCliente) {
        modeloTienda.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if ((Objects.equals(pedido.getCliente().getEmail(), emailCliente))&&(pedido.getEstado()==TipoEstado.ENVIADO)){
                sb.append(pedido.toString()).append("\n");
            }
        }
        vistaTienda.updateView(sb.toString());
    }

    ////ACTUALIZA VISTA: Crea listado de todos los pedidos enviados actualizando la vista
    private void mostrarTodosLosPedidosEnviados() {
        modeloTienda.actualizarPedidos();
        List<Pedido> listaPedidos = getListaPedidos();
        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : listaPedidos) {
            if (pedido.getEstado()==TipoEstado.ENVIADO){
                sb.append("%s\n".formatted(pedido.toString()));
            }
        }
        vistaTienda.updateView(sb.toString());
    }

}
