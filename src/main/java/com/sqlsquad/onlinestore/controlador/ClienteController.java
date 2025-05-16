package com.sqlsquad.onlinestore.controlador;

import com.sqlsquad.onlinestore.modelo.ClienteModel;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClienteEstandar;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClientePremium;

import java.util.HashMap;
import java.util.List;

/// Controlador para la Gestión de Cliente

public class ClienteController {

    private final ClienteModel clienteModel;

    // Constructor
    public ClienteController(ClienteModel clienteModel) {
        this.clienteModel = clienteModel;
    }

    // TODO verificar como hacer llegar cliente
    public void addCliente(String nombre, String domicilio, String nif, String email, Integer tipoCliente) {
        if (tipoCliente==1){
            ClientePremium cliente = new ClientePremium(nombre, domicilio, nif, email);
            clienteModel.addCliente(cliente);
            // TODO vistaTienda.updateView("Se ha creado un cliente Premium ");
        } else{
            ClienteEstandar cliente = new ClienteEstandar(nombre, domicilio, nif, email);
            clienteModel.addCliente(cliente);
            // >TODO vistaTienda.updateView("Se ha creado un cliente Estandar ");
        }
    }


    public List<Cliente> getListaClientes() {
        return clienteModel.getListaClientes();
    }

    //Comprueba que se trata de un objeto cliente premium
    private <E> boolean esPremium(E cliente){
        return cliente.getClass()== ClientePremium.class;
    }

    //Comprueba que se trata de un objeto cliente estandar
    private <E> boolean esEstandar(E cliente){
        return cliente.getClass()== ClienteEstandar.class;
    }

/*
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
        // TODO vistaTienda.updateView(sb.toString());
    }
*/
/*
    //ACTUALIZA VISTA:Registra un nuevo cliente o muestra que ya se encuentra registrado
    public void esClienteNuevo(String emailCliente){
        if (!clienteModel.existeCliente(emailCliente)){
            // TODO vistaTienda.pedirDatosCliente(emailCliente);
        } else{
            // TODO vistaTienda.updateView("Este cliente se encuentra registrado");
        }
    }
*/
    /*
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
        // TODO vistaTienda.updateView(sb.toString());
    }
     */

    /*
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
        // TODO vistaTienda.updateView(sb.toString());
    }
    */


}
