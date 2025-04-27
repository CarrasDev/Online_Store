// Grupo 2 - SQL SQUAD


package modelo.cliente;

public class Cliente {
    private Integer id;
    private String nombre;
    private String domicilio;
    private String nif;
    private String email;

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
