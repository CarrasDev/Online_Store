// Grupo 2 - SQL SQUAD


package modelo.cliente;

import jakarta.persistence.*;
import modelo.enums.TipoCliente;


@Entity
@DiscriminatorValue("ESTANDAR") // Mapeo en tabla unica "clientes"
public class ClienteEstandar extends Cliente {

    // No hay que usar esta sentencia @Enumerated(EnumType.STRING) porque generamos conflicto con @Inheritance
    @Column(name = "tipoCliente", insertable = false, updatable = false) // Gestiona Hibernate // TODO Controlar
    private TipoCliente tipoCliente;

    // Contructor para Hibernate
    public ClienteEstandar() {
        super();
    }

    // Constructor para la vista sin ID asignado
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email); // TODO Habia ID
        this.tipoCliente = TipoCliente.ESTANDAR;
    }

    // Constructor para la gestíon con ID de la BBDD
    /*
    public ClienteEstandar(Integer id, String nombre, String domicilio, String nif, String email) {
        super(id, nombre, domicilio, nif, email);
        this.tipoCliente = TipoCliente.ESTANDAR;
    }*/

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public String toString() {
        return super.toString() + " Tipo: " + tipoCliente;
    }
}
