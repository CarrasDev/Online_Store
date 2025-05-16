package com.sqlsquad.onlinestore.modelo.DTO;

import com.sqlsquad.onlinestore.modelo.entity.Articulo;
import javafx.beans.property.*;

public class ArticuloDTO {

    private final StringProperty codigoArticulo;
    private final StringProperty descripcion;
    private final FloatProperty precioVenta;
    private final FloatProperty gastosEnvio;
    private final IntegerProperty tiempoPreparacion;

    // Constructor con propiedades observables
    public ArticuloDTO(String codigoArticulo, String descripcion, Float precioVenta, Float gastosEnvio, Integer tiempoPreparacion) {
        this.codigoArticulo = new SimpleStringProperty(codigoArticulo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precioVenta = new SimpleFloatProperty(precioVenta);
        this.gastosEnvio = new SimpleFloatProperty(gastosEnvio);
        this.tiempoPreparacion = new SimpleIntegerProperty(tiempoPreparacion);
    }

    // Constructor para convertir desde la entidad Articulo
    public ArticuloDTO(Articulo articulo) {
        this(articulo.getCodigoArticulo(), articulo.getDescripcion(), articulo.getPrecioVenta(),
                articulo.getGastosEnvio(), articulo.getTiempoPreparacion());
    }

    // Metodos de acceso para propiedades observables
    public StringProperty codigoArticuloProperty() { return codigoArticulo; }
    public StringProperty descripcionProperty() { return descripcion; }
    public FloatProperty precioVentaProperty() { return precioVenta; }
    public FloatProperty gastosEnvioProperty() { return gastosEnvio; }
    public IntegerProperty tiempoPreparacionProperty() { return tiempoPreparacion; }

    // Metodo para convertir de DTO a entidad Articulo
    public Articulo toEntity() {
        return new Articulo(codigoArticulo.get(), descripcion.get(), precioVenta.get(), gastosEnvio.get(), tiempoPreparacion.get());
    }
}
