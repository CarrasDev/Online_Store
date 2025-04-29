
import modelo.Modelo;
import util.HibernateUtil;
import vista.Vista;
import controlador.Controlador;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
        HibernateUtil.getSessionFactory().openSession();
        vista.startVista(controlador);
    }
}