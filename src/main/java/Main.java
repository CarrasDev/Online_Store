
import modelo.Modelo;
import util.HibernateUtil;
import vista.Vista;
import controlador.Controlador;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // TODO Revisar secuencia de arranque de MVC
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);


        // Inicia la conexión a la BBDD
        HibernateUtil.getSessionFactory().openSession();

        // TODO --> Es clave para el arranque del programa
        vista.startVista(controlador);
    }
}