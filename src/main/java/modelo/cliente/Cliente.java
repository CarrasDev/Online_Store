// Grupo 2 - SQL SQUAD

package modelo.cliente;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Nos permite implementar toda la herencia en una sola tabla
@DiscriminatorColumn(name = "tipoCliente", discriminatorType = DiscriminatorType.STRING) // Discriminamos por tipo de cliente
public abstract class Cliente {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 70)
    private String nombre;

    @Column(name = "domicilio", nullable = false, length = 100)
    private String domicilio;

    @Column(name = "nif", nullable = false, length = 20)
    private String nif;

    @Id
    @Column(name = "email", nullable = false, unique = true, length = 30)
    private String email;

    // Constructor para Hibernate
    public Cliente() {}

    public Cliente(Integer id, String nombre, String domicilio, String nif, String email) {
        this.id = id;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }

    // Sobrecarga del constructor para gestión sin número de cliente. BBDD Asignará el Nº de cliente
    public Cliente(String nombre, String domicilio, String nif, String email) {
        this(null, nombre, domicilio, nif, email);
    }

    public Integer getId(){
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDomicilio() {
        return domicilio;
    }
    public String getNif() {
        return nif;
    }
    public String getEmail() {
        return email;
    }
    public void setId(int id) {this.id = id;}
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public void setNif(String nif) {
        this.nif = nif;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Nombre: " + nombre + " Domicilio: " + domicilio + " Nif: " + nif + " Email: " + email;
    }
}
