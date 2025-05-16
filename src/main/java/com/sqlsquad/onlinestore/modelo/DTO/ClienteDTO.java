package com.sqlsquad.onlinestore.modelo.DTO;

import com.sqlsquad.onlinestore.modelo.entity.cliente.Cliente;
import com.sqlsquad.onlinestore.modelo.entity.cliente.ClientePremium;
import javafx.beans.property.*;

public class ClienteDTO {

    private final StringProperty nif;
    private final StringProperty nombre;
    private final StringProperty email;
    private final StringProperty domicilio;
    private final StringProperty tipoCliente;
    private final FloatProperty cuotaAnual;
    private final IntegerProperty descuento;

    // Constructor para convertir la clase Cliente
    public ClienteDTO(Cliente cliente) {
        this.nif = new SimpleStringProperty(cliente.getNif());
        this.nombre = new SimpleStringProperty(cliente.getNombre());
        this.email = new SimpleStringProperty(cliente.getEmail());
        this.domicilio = new SimpleStringProperty(cliente.getDomicilio());
        this.tipoCliente = new SimpleStringProperty(cliente.getTipoCliente().name());

        if (cliente instanceof ClientePremium) {
            this.cuotaAnual = new SimpleFloatProperty(((ClientePremium) cliente).getCuotaAnual());
            this.descuento = new SimpleIntegerProperty(((ClientePremium) cliente).getDescuento());
        } else {
            this.cuotaAnual = new SimpleFloatProperty(0.0f);
            this.descuento = new SimpleIntegerProperty(0);
        }
    }

    // Metodos de acceso para propiedades observables
    public StringProperty nifProperty() { return nif; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty emailProperty() { return email; }
    public StringProperty domicilioProperty() { return domicilio; }
    public StringProperty tipoClienteProperty() { return tipoCliente; }
    public FloatProperty cuotaAnualProperty() { return cuotaAnual; }
    public IntegerProperty descuentoProperty() { return descuento; }

    // TODO Metodo para pasar de DTO a entidad Cliente

}
