package modelo;

// Grupo 2 - SQL SQUAD

import jakarta.persistence.*;

// TODO Verificar correcta implementación del mapeo de la BBDD a la clase Articulo

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

    // Constructor sin ID asignado
    public Articulo() {
    }

    public Articulo(String codigoArticulo, String descripcion, Float precioVenta, Float gastosEnvio, Integer tiempoPreparacion) {
        this.codigoArticulo = codigoArticulo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Float getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(Float gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public Integer getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(Integer tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public String toString() {
        return "Código: " + codigoArticulo + " [Descripcion: " + descripcion + ", Precio: " + precioVenta
                + "€, G.Envio: " + gastosEnvio + "€, Tiempo Pre.: " + tiempoPreparacion + " min]";
    }
}
