package modelo;

// Grupo 2 - SQL SQUAD


public class Articulo {

    private String codigoArticulo;
    private String descripcion;
    private Float precioVenta;
    private Float gastosEnvio;
    private Integer tiempoPreparacion;

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
