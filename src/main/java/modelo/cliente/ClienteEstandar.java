// Grupo 2 - SQL SQUAD


package modelo.cliente;

import jakarta.persistence.*;
import modelo.enums.TipoCliente;


@Entity
@DiscriminatorValue("ESTANDAR")
public class ClienteEstandar extends Cliente {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoCliente", nullable = false)
    private final TipoCliente tipoCliente;

    // TODO verificar uso de super() en el constructor
    // Contructor para Hibernate
    public ClienteEstandar() {}

    // Constructor para la vista sin ID asignado
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(null, nombre, domicilio, nif, email);
        this.tipoCliente = TipoCliente.ESTANDAR;
    }

    // Constructor para la gestíon con ID de la BBDD
    public ClienteEstandar(Integer id, String nombre, String domicilio, String nif, String email) {
        super(id, nombre, domicilio, nif, email);
        this.tipoCliente = TipoCliente.ESTANDAR;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public String toString() {
        return super.toString() + " Tipo: " + tipoCliente;
    }
}
