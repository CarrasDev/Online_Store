package controlador;

import modelo.Articulo;

import java.util.List;

/// Controlador para la Gestión de Artículos

public class ArticuloController {

    // TODO Desarrollo ArticuloController

    // TODO variables --> Modelo y Vista correspondiente

    // TODO Constructor

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


}
