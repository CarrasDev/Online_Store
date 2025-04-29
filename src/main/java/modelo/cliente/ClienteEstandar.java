// Grupo 2 - SQL SQUAD


package modelo.cliente;

import jakarta.persistence.*;
import modelo.enums.TipoCliente;


@Entity
@DiscriminatorValue("ESTANDAR") // Mapeo en tabla unica "clientes"
public class ClienteEstandar extends Cliente {

    // Contructor para Hibernate
    public ClienteEstandar() {
        super();
    }

    // Constructor para la vista sin ID asignado
    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email, TipoCliente.ESTANDAR);
    }

    public TipoCliente getTipoCliente() {
        return TipoCliente.ESTANDAR;
    }

    @Override
    public String toString() {
        return super.toString() + " Tipo: " + TipoCliente.ESTANDAR;
    }
}
