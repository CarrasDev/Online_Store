package controlador;

import modelo.cliente.ClienteEstandar;
import modelo.cliente.ClientePremium;

import java.util.HashMap;

/// Controlador para la Gestión de Cliente

public class ClienteController {

    // TODO Desarrollo ClienteController

    // TODO variables --> Modelo y Vista correspondiente

    // TODO Constructor

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
        if (!esClienteRegistrado(emailCliente)){
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


}
