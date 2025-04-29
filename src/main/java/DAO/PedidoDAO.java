package DAO;

import modelo.Pedido;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pedido", Pedido.class).getResultList(); // JPA
        } catch (Exception e) {
            System.err.println("Error al acceder a todos los Pedidos de la BBDD " + e.getMessage());
        }
        return List.of();
    }

    @Override
    public void save(Pedido pedido) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al guardar el pedido en la BBDD" + e.getMessage());
        }
    }

    @Override
    public void update(Pedido pedido) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar el pedido en la BBDD" + e.getMessage());
        }
    }

    @Override
    public void delete(Pedido pedido) {
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(pedido);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al eliminar el pedido de la BBDD" + e.getMessage());
        }
    }
}
