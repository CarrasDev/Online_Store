package DAO;

import modelo.Pedido;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class PedidoDAO implements IDao<Pedido> {


    @Override
    public Optional<Pedido> getById(String id) {
        // TODO Pendiente implementar
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Pedido pedido = session.get(Pedido.class, Integer.parseInt(id));  //Integer.parseInt(id) --> de string a Int
            return Optional.ofNullable(pedido);
        } catch (Exception e) {
            System.err.println("Error al acceder a la BBDD con id: " + id + " " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Pedido> getAll() {
        // TODO Pendiente implementar
        return List.of();
    }

    @Override
    public void save(Pedido pedido) {
        // TODO Pendiente implementar
    }

    @Override
    public void update(Pedido pedido) {
        // TODO Pendiente implementar
    }

    @Override
    public void delete(Pedido pedido) {
        // TODO Pendiente implementar
    }
}
