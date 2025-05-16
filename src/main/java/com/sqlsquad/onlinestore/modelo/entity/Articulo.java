package com.sqlsquad.onlinestore.modelo.entity;

import jakarta.persistence.*;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


@Entity
@Table(name = "articulo")
public class Articulo {

    @Id
    @Column(name = "codigoArticulo", length = 20, nullable = false)
    private String codigoArticulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;

    @Column(name = "precioVenta", nullable = false)
    private Float precioVenta;

    @Column(name = "gastosEnvio", nullable = false)
    private Float gastosEnvio;

    @Column(name = "tiempoPreparacion", nullable = false)
    private Integer tiempoPreparacion;

    // Propiedades observables para JavaFX
    private final StringProperty codigoArticuloProperty = new SimpleStringProperty();
    private final StringProperty descripcionProperty = new SimpleStringProperty();
    private final FloatProperty precioVentaProperty = new SimpleStringProperty();
    private final FloatProperty gastosEnvioProperty = new SimpleStringProperty();
    private final IntegerProperty tiempoPreparacionProperty = new SimpleStringProperty();

    // Constructor sin ID asignado
    public Articulo() {
    }

    public Articulo(String codigoArticulo, String descripcion, Float precioVenta, Float gastosEnvio, Integer tiempoPreparacion) {
        this.codigoArticulo = codigoArticulo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;

        // Asignar propiedades observables
        codigoArticuloProperty.set(codigoArticulo);
        descripcionProperty.set(descripcion);
        precioVentaProperty.set(precioVenta);
        gastosEnvioProperty.set(gastosEnvio);
        tiempoPreparacionProperty.set(tiempoPreparacion);
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }
    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
        codigoArticuloProperty.set(codigoArticulo);
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        descripcionProperty.set(descripcion);
    }

    public Float getPrecioVenta() {
        return precioVenta;
    }
    public void setPrecioVenta(Float precioVenta) {
        this.precioVenta = precioVenta;
        precioVentaProperty.set(precioVenta);
    }

    public Float getGastosEnvio() {
        return gastosEnvio;
    }
    public void setGastosEnvio(Float gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
        gastosEnvioProperty.set(gastosEnvio);
    }

    public Integer getTiempoPreparacion() {
        return tiempoPreparacion;
    }
    public void setTiempoPreparacion(Integer tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
        tiempoPreparacionProperty.set(tiempoPreparacion);
    }

    // Metodos para JavaFX
    public StringProperty codigoArticuloProperty() {
        return codigoArticuloProperty;
    }
    public StringProperty getDescripcionProperty() {
        return descripcionProperty;
    }
    public FloatProperty precioVentaProperty() {
        return precioVentaProperty;
    }
    public FloatProperty gastosEnvioProperty() {
        return gastosEnvioProperty;
    }
    public IntegerProperty tiempoPreparacionProperty() {
        return tiempoPreparacionProperty;
    }

    @Override
    public String toString() {
        return "Código: " + codigoArticulo + " [Descripcion: " + descripcion + ", Precio: " + precioVenta
                + "€, G.Envio: " + gastosEnvio + "€, Tiempo Pre.: " + tiempoPreparacion + " min]";
    }
}
