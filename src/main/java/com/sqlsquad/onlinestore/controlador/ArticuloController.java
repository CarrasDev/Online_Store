package com.sqlsquad.onlinestore.controlador;

import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import com.sqlsquad.onlinestore.modelo.ArticuloModel;


import java.util.List;

/// Controlador para la Gestión de Artículos

public class ArticuloController {

    private final ArticuloModel articuloModel;

    // Constructor
    public ArticuloController(ArticuloModel articuloModel) {
        this.articuloModel = articuloModel;
    }

    public void addArticulo(Articulo articulo) {
        articuloModel.addArticulo(articulo);
    }

    //LEE MODELO:Busca un articulo en el modelo y devuelve TRUE si lo encuentra
    public boolean existeArticulo(String codigoArticulo) {
        return (articuloModel.existeArticulo(codigoArticulo));
    }

    //LEE MODELO:Obtiene y retorna un listado de todos los articulos del modelo
    public List<Articulo> getListaArticulos() {
        return articuloModel.getArticulos();
    }
}
