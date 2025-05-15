package com.sqlsquad.onlinestore.modelo;

import com.sqlsquad.onlinestore.DAO.FactoryDAO;
import com.sqlsquad.onlinestore.DAO.IDao;
import com.sqlsquad.onlinestore.modelo.entity.Articulo;

import java.util.ArrayList;
import java.util.Collection;

public class ArticuloModel {


    //Añaden a listados
    public void addArticulo(Articulo articulo) {
        IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
        articuloDAO.save(articulo);
    }

    public Articulo getArticulo(String codigoArticulo) {
        IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
        return articuloDAO.getById(codigoArticulo).orElse(null);
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

    public boolean existeArticulo(String codigoArticulo) {
        IDao<Articulo> articuloDAO = FactoryDAO.getIDAO("ARTICULO");
        return articuloDAO.getById(codigoArticulo).isPresent();
    }
}
