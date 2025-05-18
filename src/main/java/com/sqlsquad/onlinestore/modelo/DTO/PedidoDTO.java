package com.sqlsquad.onlinestore.modelo.DTO;

import com.sqlsquad.onlinestore.modelo.entity.Pedido;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class PedidoDTO extends Pedido {

    private final IntegerProperty numeroPedido;
    private final StringProperty codigoArticulo;
    private final IntegerProperty cantidadArticulos;
    private final StringProperty emailCliente;
    private final DoubleProperty precioTotal;
    private final ObjectProperty<LocalDateTime> fechaPedido;
    private final StringProperty tipoEstado;

    // Constructor para convertir la clase Pedido
    public PedidoDTO(Pedido pedido) {
        this.numeroPedido = new SimpleIntegerProperty(pedido.getNumeroPedido());
        this.codigoArticulo = new SimpleStringProperty(pedido.getCodigoArticulo());
        this.cantidadArticulos = new SimpleIntegerProperty(pedido.getCantidadArticulos());
        this.emailCliente = new SimpleStringProperty(pedido.getEmailCliente());
        this.precioTotal = new SimpleDoubleProperty(pedido.getPrecioTotal());
        this.fechaPedido = new SimpleObjectProperty<>(pedido.getFechaPedido());
        this.tipoEstado = new SimpleStringProperty(pedido.getTipoEstado().name());
    }

    // Metodos de acceso para propiedades observables
    public IntegerProperty numeroPedidoProperty() { return numeroPedido; }
    public StringProperty codigoArticuloProperty() { return codigoArticulo; }
    public IntegerProperty cantidadArticulosProperty() { return cantidadArticulos; }
    public StringProperty emailClienteProperty() { return emailCliente; }
    public DoubleProperty precioTotalProperty() { return precioTotal; }
    public ObjectProperty<LocalDateTime> fechaPedidoProperty() { return fechaPedido; }
    public StringProperty tipoEstadoProperty() { return tipoEstado; }

    // TODO metodo para pasar de DTO a entidad Pedido

}