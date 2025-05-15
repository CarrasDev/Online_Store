package com.sqlsquad.onlinestore.controlador;

import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import com.sqlsquad.onlinestore.modelo.ArticuloModel;


import java.util.List;

/// Controlador para la Gestión de Artículos

public class ArticuloController {

    // TODO variables --> Modificar uso de Vista según proceda
    private final ArticuloModel articuloModel;


    // Constructor
    public ArticuloController(ArticuloModel articuloModel) {
        this.articuloModel = articuloModel;
    }

    public void addArticulo(Articulo articulo) {
        articuloModel.addArticulo(articulo);
    }

    /*
    public void addArticulo(String codigoArticulo, String descripcion, Float precioVenta, Float gastosEnvio, Integer tiempoPreparacion) {
        Articulo articulo = new Articulo(codigoArticulo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
        articuloModel.addArticulo(articulo);
    }
    */

    //LEE MODELO:Busca un articulo en el modelo y devuelve TRUE si lo encuentra
    public boolean existeArticulo(String codigoArticulo) {
        return (articuloModel.existeArticulo(codigoArticulo));
    }

    //LEE MODELO:Obtiene y retorna un listado de todos los articulos del modelo
    private <E> List<E> getListaArticulos() {
        return ((List<E>) articuloModel.getArticulos());
    }

    /*
    //ACTUALIZA VISTA: Elabora listado de artículos y actualiza vista para mostrarlos
    public <E> void mostrarArticulos() {
        List<E> listaArticulos = this.getListaArticulos();
        StringBuilder sb = new StringBuilder();
        for (E articulo : listaArticulos) {
            sb.append(articulo.toString()).append("\n");
        }
         vistaTienda.updateView(sb.toString());
    }
    */

     /*
    //Comprueba si un codigo de artículo existe y procede con
    // el proceso de creacion o actualiza vista para mostrar duplicado
    public void nuevoCodigoArticulo(String codigoArticulo){
        if (!existeArticulo(codigoArticulo)){
            vistaTienda.pedirDatosArticulo(codigoArticulo);
        } else{
            vistaTienda.updateView("Artículo duplicado\n");
        }
    }
    */


}
