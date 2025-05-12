
import controlador.ArticuloController;
import controlador.ClienteController;
import controlador.PedidoController;
import modelo.ArticuloModel;
import modelo.ClienteModel;
import modelo.PedidoModel;
import util.HibernateUtil;
import vista.Vista;


public class Main {
    public static void main(String[] args) {

        // Declaración de modelos
        ArticuloModel articuloModel = new ArticuloModel();
        ClienteModel clienteModel = new ClienteModel();
        PedidoModel pedidoModel = new PedidoModel();

        // Declaración de vistas
        Vista vista = new Vista();

        // Relación MVC
        ArticuloController articuloController = new ArticuloController(articuloModel, vista);
        ClienteController clienteController = new ClienteController(clienteModel, vista);
        PedidoController pedidoController = new PedidoController(pedidoModel,articuloModel, clienteModel, vista);

        // Inicia la conexión a la BBDD
        HibernateUtil.getSessionFactory().openSession();
        // La sesión se está cerrando en el menú principal de la vista, case 0.
        // TODO Con cambio a JavaFX hay que controlar donde cerramos la BBDD

        // TODO provisional para 1 vista
        vista.startVista(articuloController, clienteController, pedidoController);
    }
}