package com.sqlsquad.onlinestore.modelo.entity;

import jakarta.persistence.*;
import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClientePremium;
import com.sqlsquad.onlinestore.modelo.enums.TipoEstado;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "pedido")
public class Pedido {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeroPedido")
    private Integer numeroPedido;

    @ManyToOne
    @JoinColumn(name = "codigoArticulo", nullable = false)
    private Articulo articulo;

    @Column(name = "cantidadArticulos", nullable = false)
    private Integer cantidadArticulos;

    @ManyToOne
    @JoinColumn(name = "emailCliente", nullable = false)
    private Cliente cliente;

    @Column(name = "precioTotal", nullable = false)
    private Double precioTotal;

    @Column(name = "fechaPedido", nullable = false)
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private TipoEstado estado;

    // Constructor vacío obligatorio para Hibernate
    public Pedido() {
    }

    public Pedido(Integer numeroPedido, Articulo articulo, Integer cantidadArticulos,
                  Cliente cliente, LocalDateTime fechaPedido, TipoEstado estadoPedido) {
        this.numeroPedido = numeroPedido;
        this.articulo = articulo;
        this.cantidadArticulos = cantidadArticulos;
        this.cliente = cliente;
        this.precioTotal = calcularTotal();
        this.fechaPedido = fechaPedido; //LocalDateTime.now();
        this.estado = estadoPedido; //TipoEstado.PENDIENTE;
    }

    // Sobrecarga de constructor para gestión sin número de pedído. BBDD asignará el Nº de pedido
    public Pedido(Articulo articulo, Integer cantidadArticulos,Cliente cliente,
                  LocalDateTime fechaPedido, TipoEstado estadoPedido) {
        this(null, articulo, cantidadArticulos, cliente, fechaPedido, estadoPedido);
    }

    // Metodo con pruebas unitarias
    public double calcularTotal() {
        // Calcular gastos de envío
        double gastosEnvio;
        if (cliente instanceof ClientePremium clientePremium) {
            gastosEnvio = articulo.getGastosEnvio() * (1.0 - clientePremium.getDescuento() / 100.0);
        } else {
            gastosEnvio = articulo.getGastosEnvio();
        }

        // Calcular total
        return gastosEnvio + (articulo.getPrecioVenta() * cantidadArticulos);
    }

    // Metodo con pruebas unitarias
    public boolean actualizarEstadoPreparacion() {
        if (estado == TipoEstado.PENDIENTE) {
            // Calcular el tiempo transcurrido
            long tiempoTranscurrido = Duration.between(fechaPedido, LocalDateTime.now()).toMinutes();

            // Actualizar estado se ha cumplido el tiempo de preparación
            if (tiempoTranscurrido >= articulo.getTiempoPreparacion()) {
                estado = TipoEstado.ENVIADO;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public TipoEstado getEstado() {
        return estado;
    }

    private void setEstado(TipoEstado estado) {
        this.estado = estado;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public String getCodigoArticulo() {
        return articulo.getCodigoArticulo();
    }

    public String getEmailCliente() {
        return cliente.getEmail();
    }

    private void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidadArticulos() {
        return cantidadArticulos;
    }

    private void setCantidadArticulos(Integer cantidadArticulos) {
        this.cantidadArticulos = cantidadArticulos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    private void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public TipoEstado getTipoEstado() {
        return estado;
    }

    public void setTipoEstado(TipoEstado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String fechaConFormato = fechaPedido.format(formatoFecha);
        String horaConFormato = fechaPedido.format(formatoHora);

        StringBuilder sb = new StringBuilder();

        sb.append("----- Pedido -----\n");
        sb.append("\tNª Pedido: " + numeroPedido + "\n");
        sb.append("\tFecha: " + fechaConFormato + "\n");
        sb.append("\tHora: " + horaConFormato + "\n");
        sb.append("\tCliente: " + cliente.getNombre() + "\n");
        sb.append("\tCodigo: " + articulo.getCodigoArticulo() + "\n");
        sb.append("\tDescripción: " + articulo.getDescripcion() + "\n");
        sb.append("\tCantidad: " + cantidadArticulos + "\n");
        sb.append("\tPrecio: ");
        sb.append(String.format("%.2f", precioTotal));
        sb.append(" €\n");
        sb.append("\tEstado: " + estado + "\n");

        return sb.toString();
    }
}
