package DAO;

// Factory nos permite clasificar nuestros DAOS y tener una única forma centralizada para crear Objetos DAO

public class FactoryDAO {

    public static IDao getIDAO(String daoType) {
        switch (daoType) {
            case "CLIENTE":
                return new ClienteDAO();
            case "ARTICULO":
                return new ArticuloDAO();
            case "PEDIDO":
                return new PedidoDAO();
            default:
                throw new IllegalArgumentException("Tipo de DAO Incorrecto");
        }
    }
}

// Como usarlo:
/*
Ejemplo:

IDao articuloDAO = FactoryDAO.getDAO("ARTICULO");
articuloDAO.get(1) // Obtiene el artículo con ID "1" de la BBDD

 */
