package controlador;

// el paquete controlador únicamente contendrá la clase Controlador, que hará de puente entre la vista y el modelo.
// La vista sólo podrá utilizar esta clase para acceder a la información del modelo.

import modelo.entity.Articulo;
import modelo.Modelo;
import modelo.entity.Pedido;
import modelo.entity.cliente.Cliente;
import modelo.entity.cliente.ClienteEstandar;
import modelo.entity.cliente.ClientePremium;
import modelo.enums.TipoEstado;
import vista.Vista;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Controlador {
    private final Modelo modeloTienda;
    private final Vista vistaTienda;
    public Controlador(Modelo modeloTienda, Vista vistaTienda) {
        this.modeloTienda = modeloTienda;
        this.vistaTienda = vistaTienda;
    }
    ////////////// GESTION DE ARTÍCULOS ///////////////////////////////////////
    //ACTUALIZA MODELO: Instancia y añade un artículo al modelo.
    public void addArticulo(String codigoArticulo, String descripcion, Float precioVenta, Float gastosEnvio, Integer tiempoPreparacion) {
        Articulo articulo = new Articulo(codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        modeloTienda.addArticulo(articulo);
    }
    //LEE MODELO:Busca un articulo en el modelo y devuelve TRUE si lo encuentra
    public boolean existeArticulo(String codigoArticulo) {
        return (modeloTienda.existeArticulo(codigoArticulo));
    }
    //LEE MODELO:Obtiene y retorna un listado de todos los articulos del modelo
    private <E> List<E> getListaArticulos() {
        return ((List<E>) modeloTienda.getArticulos());
    }

    //ACTUALIZA VISTA: Elabora listado de artículos y actualiza vista para mostrarlos
    public <E> void mostrarArticulos() {
        List<E> listaArticulos = this.getListaArticulos();
        StringBuilder sb = new StringBuilder();
        for (E articulo : listaArticulos) {
            sb.append(articulo.toString()).append("\n");
        }
        vistaTienda.updateView(sb.toString());
    }

    //Comprueba si un codigo de artículo existe y procede con
    // el proceso de creacion o actualiza vista para mostrar duplicado
    public void nuevoCodigoArticulo(String codigoArticulo){
        if (!existeArticulo(codigoArticulo)){
            vistaTienda.pedirDatosArticulo(codigoArticulo);
        } else{
            vistaTienda.updateView("Artículo duplicado\n");
        }
    }

    ///////////////////////Gestión de Clientes//////////////////////////
    /// Permite añadir un cliente al modelo y actualiza la vista para
    /// Indicar que se ha creado.

    public void addCliente(String nombre, String domicilio, String nif, String email, Integer tipoCliente) {
        if (tipoCliente==1){
            ClientePremium cliente = new ClientePremium(nombre, domicilio, nif, email);
            modeloTienda.addCliente(cliente);
            vistaTienda.updateView("Se ha creado un cliente Premium ");
        } else{
            ClienteEstandar cliente = new ClienteEstandar(nombre, domicilio, nif, email);
            modeloTienda.addCliente(cliente);
            vistaTienda.updateView("Se ha creado un cliente Estandar ");
        }
    }
    //LEE MODELO:Recupera cliente del modelo
    //private <T> T getCliente(String email) {
    //    return ((T) modeloTienda.getCliente(email));
    //}
    //LEE MODELO:Recupera el listado de clientes del modelo
    private <K,V> HashMap<K,V> getListaClientes() {
        return ((HashMap<K,V>) modeloTienda.getListaClientes());
    }
    //LEE MODELO:Comprueba si existe el cliente, devuelve true si está registrado.
    public boolean esClienteRegistrado(String emailCliente) {
        return modeloTienda.getCliente(emailCliente) != null;
    }
    //Comprueba que se trata de un objeto cliente premium
    private <E> boolean esPremium(E cliente){
        return cliente.getClass()== ClientePremium.class;
    }
    //Comprueba que se trata de un objeto cliente estandar
    private <E> boolean esEstandar(E cliente){
        return cliente.getClass()== ClienteEstandar.class;
    }
    ////ACTUALIZA VISTA:Envia el listado de clientes a la vista en formato String para
    //que se muestre al usuario mediante updateView
    public <K,V> void mostrarClientes() {
        HashMap<K,V> listaClientes = getListaClientes();
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de clientes: \n");
        for (K clave : listaClientes.keySet()) {
            sb.append(listaClientes.get(clave).toString()).append("\n");
        }
        //Envía la cadena a la vista y hace update
        vistaTienda.updateView(sb.toString());
    }

    //ACTUALIZA VISTA:Registra un nuevo cliente o muestra que ya se encuentra registrado
    public void esClienteNuevo(String emailCliente){
        if (!esClienteRegistrado(emailCliente)){                // TODO Controlar su uso
            vistaTienda.pedirDatosCliente(emailCliente);
        } else{
            vistaTienda.updateView("Este cliente se encuentra registrado");
        }
    }

    //ACTUALIZA VISTA: Muestra un listado de clientes premium en la vista
    public <K,V> void mostrarClientesPremium() {
        HashMap<K,V> listaClientes = getListaClientes();
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de clientes: \n");
        for (K clave : listaClientes.keySet()) {
            if (esPremium(listaClientes.get(clave))) {
                sb.append(listaClientes.get(clave).toString()).append("\n");
            }
        }
        vistaTienda.updateView(sb.toString());
    }

    //Muestra un listado de clientes estandar en la vista
    public <K,V> void mostrarClientesEstandar() {
        HashMap<K,V> listaClientes = getListaClientes();
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de clientes: \n");
        for (K clave : listaClientes.keySet()) {
            if (esEstandar(listaClientes.get(clave))) {
                sb.append(listaClientes.get(clave).toString()).append("\n");
            }
        }
        //Envía la cadena a la vista y hace update
        vistaTienda.updateView(sb.toString());
    }

    ///////////////////////Gestión de Pedidos//////////////////////////
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
            if (!esClienteRegistrado(iDcliente)) {  // TODO Verificando
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
            if (!esClienteRegistrado(emailCliente)) {       // TODO Verificando
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
