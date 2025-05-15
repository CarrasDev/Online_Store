package com.sqlsquad.onlinestore.modelo.entity.cliente;

import jakarta.persistence.*;
import modelo.enums.TipoCliente;



@Entity
@DiscriminatorValue("PREMIUM") // Mapeo en tabla unica "clientes"
public class ClientePremium extends Cliente {

    @Column(name = "descuento", nullable = true)
    private int descuento;

    @Column(name = "cuotaAnual", nullable = true)
    private Float cuotaAnual;

    //Constructor para Hibernate
    public ClientePremium() {
        super();
    }

    // Constructor para la vista sin ID asignado
    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email, TipoCliente.PREMIUM);
        this.descuento = 20;
        this.cuotaAnual = 30.0f;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public TipoCliente getTipoCliente() {
        return TipoCliente.PREMIUM;
    }

    public Float getCuotaAnual() {
        return cuotaAnual;
    }
    public void setCuotaAnual(Float cuotaAnual) {
        this.cuotaAnual = cuotaAnual;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return super.toString() + " Descuento: " + descuento + " Tipo: " + TipoCliente.PREMIUM;
    }
}
