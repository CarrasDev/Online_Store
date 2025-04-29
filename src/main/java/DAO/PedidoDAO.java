package DAO;

import modelo.Pedido;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class PedidoDAO implements IDao<Pedido> {


    @Override
    public Optional<Pedido> getById(String id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Pedido pedido = session.get(Pedido.class, Integer.parseInt(id));  //Integer.parseInt(id) --> de string a Int
            return Optional.ofNullable(pedido);
        } catch (Exception e) {
            System.err.println("Error al acceder al Pedido con id: " + id + " " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public List<Pedido> getAll() {
        // TODO Pendiente implementar
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).getResultList(); // JPA
        } catch (Exception e) {
            System.err.println("Error al acceder a todos los Pedidos de la BBDD " + e.getMessage());
        }
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
